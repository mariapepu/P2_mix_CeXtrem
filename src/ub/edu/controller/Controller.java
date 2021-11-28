package ub.edu.controller;

import ub.edu.model.*;
import ub.edu.resources.service.AbstractFactoryData;
import ub.edu.resources.service.DataService;
import ub.edu.resources.service.FactoryMOCK;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {
    private final DataService dataService;         // Connexio amb les dades
    private CarteraSocis carteraSocis;   // Model
    private CatalegExcursions catalegExcursions;
    private Map<String, Pagament> pagamentsMap;


    public Controller() {
        // Origen de les dades
        AbstractFactoryData factory = new FactoryMOCK();
        dataService = new DataService(factory);

        try {
            iniCarteraSocis();
            iniCatalegExcursions();
            initExcursionsEspecie(); //carreguem les especies a les excursions
        } catch (Exception e) {
            e.getStackTrace();
        }

        iniPagamentsMap();

    }

   /*-----------------------------------------------------------------------------------------------------------------
             inits
    ----------------------------------------------------------------------------------------------------------------*/

    public void iniCarteraSocis() throws Exception {
        List<Soci> l = dataService.getAllSocis();
        if (l != null) {
            carteraSocis = new CarteraSocis(l);
        }
    }

    public void iniCatalegExcursions() throws Exception {
        List<Excursio> m = dataService.getAllExcursions();
        if (m != null) {
            catalegExcursions = new CatalegExcursions(dataService.getAllExcursions().stream()
                    .collect(Collectors.toMap(Excursio::getNom, Function.identity())));
        }
    }

    public void initExcursionsEspecie() {
        for (Map.Entry<Especie, ArrayList<String>> entry : dataService.getLlistaEspecies().entrySet()) {
            Especie especie = entry.getKey();
            List<String> excursions = entry.getValue();
            for (String nomExcursio : excursions) {
                catalegExcursions.find(nomExcursio).addEspecie(especie);
            }
        }
    }

    /*-----------------------------------------------------------------------------------------------------------------
            PAGAMENTS init
     ----------------------------------------------------------------------------------------------------------------*/

    public void iniPagamentsMap() {
        pagamentsMap = new HashMap<>();
        addPagament("Delta de l'Ebre", "Ciclisme", "0", "ajaleo@gmail.com", false);
        addPagament("Delta de l'Ebre", "Kayak", "1", "ajaleo@gmail.com", false);
        addPagament("La Foradada", "Escalada", "2", "ajaleo@gmail.com", true);
        addPagament("La Foradada", "Escalada", "3", "dtomacal@yahoo.cat", false);
        addPagament("Delta de l'Ebre", "Kayak", "4", "dtomacal@yahoo.cat", false);

    }

    private void addPagament(String e, String a, String id_transaccio, String soci, boolean estat) {
        Excursio exc = catalegExcursions.find(e);
        Activitat act = exc.getActivitat(a);
        Soci s = carteraSocis.find(soci);
        Pagament p = new Pagament(exc, act, id_transaccio, s, estat);
        pagamentsMap.put(id_transaccio, p);
        s.addPagament(id_transaccio, p);

        //sol per inicialitzar les estadistiques
        if (p.getEstaPagat()) {
            p.setMetodePagament(new CompteBancari(soci, "ES66 6666 6666 6666"));
        }
    }

     /*------------------------------------------------------------------------------------------------------------------------------------------
        LOGUEJAR SOCI
     ---------------------------------------------------------------------------------------------------------------------------------------- */

    public String loguejarSoci(String username, String password) {
        return carteraSocis.loguejarSoci(username, password);
    }

    public String recuperarContrassenya(String username) {
        return carteraSocis.recuperarContrassenya(username);
    }

    public String validateRegisterSoci(String username, String password) {
        int esValid = carteraSocis.validateRegisterSoci(username, password);

        return switch (esValid) {
            case -1 -> "El nom ja existeix";
            case -2 -> "Contrassenya no vàlida";
            case -3 -> "Email incorrecte";
            default -> "Soci creat correctament";
        };
    }

    public String findSoci(String a) {
        if (carteraSocis.find(a) != null) {
            return "Soci existent";
        }
        return "";
    }

    public String validatePassword(String b) {
        if (!carteraSocis.validatePassword(b)) {
            return "Contrassenya no segura";
        } else {
            return "Contrassenya segura";
        }
    }

    public String validateUsername(String a) {
        if (!carteraSocis.isMail(a)) {
            return "Correu en format incorrecte";
        } else {
            return "Correu en format correcte";
        }
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------
        PAGAR
     ---------------------------------------------------------------------------------------------------------------------------------------- */
    public String addCompteBanc(String soci, String iban) {
        return carteraSocis.addCompteBancari(soci, iban);
    }

    public String pagarCompteBanc(String soci, String nomExc, String nomAct) {
        Activitat act = catalegExcursions.getActivitatByName(nomExc, nomAct);
        Soci s = carteraSocis.find(soci);
        try {
            Pagament p = s.findPagamentByAct(act);
            if (!p.getEstaPagat()) {
                if (s.getCompteBancari() != null) {
                    printInfoPagament(p, s.getCompteBancari());
                    p.setMetodePagament(s.getCompteBancari());
                    return s.pagarCompteBancari(p);
                } else {
                    return "L'usuari no te compte bancari asociat";
                }

            } else {
                return "L'excusió ja esta pagada";
            }
        } catch (Exception e) {
            return "No s'ha trobat el pagament";
        }
    }

    public String pagar(String soci, String nomExc, String nomAct, MetodePagament mp) {
        Soci s = carteraSocis.find(soci);
        Activitat act = catalegExcursions.getActivitatByName(nomExc, nomAct);

        try {
            Pagament p = s.findPagamentByAct(act);
            if (!p.getEstaPagat()) {
                printInfoPagament(p, mp);
                p.setMetodePagament(mp);
                return s.pagar(p, mp);
            } else {
                return "L'excusió ja esta pagada";
            }
        } catch (Exception e) {
            return "No s'ha trobat el pagament";
        }
    }

    public String llistarPagaments(String soci) {
        Soci s = carteraSocis.find(soci);
        return s.llistarPagaments();
    }

    public void reservarActivitat(String nomUsuari, String nomExc, String nomAct) {
        Soci s = carteraSocis.find(nomUsuari);
        int i = pagamentsMap.size();
        String str = "";
        str += i;
        Pagament p = new Pagament(catalegExcursions.find(nomExc), catalegExcursions.getActivitatByName(nomExc, nomAct), str, s, false);
        s.addPagament(str, p);
        pagamentsMap.put(str, p);
    }

    public void printInfoPagament(Pagament p, MetodePagament mp) {
        System.out.println("--------------------------------------");
        System.out.println("Mètode de pagament: " + mp.nomMetode());
        System.out.println("--------------------------------------");
        p.printInfo();
        System.out.println(" ");
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------
        ESPECIE
     ---------------------------------------------------------------------------------------------------------------------------------------- */
    public void afegirEspecieExcursio(String nomEspecie, String nomExcursio) {
        catalegExcursions.afegirEspecieExcursio(nomEspecie, nomExcursio);
    }

    public Iterable<String> cercaExcursions() throws Exception {
        return catalegExcursions.cercaExcursions();
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------
       EXCURSIO
    ---------------------------------------------------------------------------------------------------------------------------------------- */
    public Iterable<String> llistarCatalegExcursionsPerNom() {
        return catalegExcursions.llistarCatalegExcursionsPerNom();
    }

    public Iterable<String> llistarCatalegExcursionsPerData() {
        return catalegExcursions.llistarCatalegExcursionsPerData();
    }

    public void addActivitat(String nomExcursio, String nomActivitat, float preu) throws IllegalArgumentException {
        catalegExcursions.addActivitat(nomExcursio, nomActivitat, preu);
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------
        COMENTARIS
     ---------------------------------------------------------------------------------------------------------------------------------------- */
    public String comentarActivitat(String nomSoci, String nomExcursio, String nomActivitat, String comentari) {

        if (carteraSocis.find(nomSoci) == null) {
            return "Correu inexistent";
        }

        if (catalegExcursions.find(nomExcursio) == null) {
            return "Excursio no existent";
        }

        if (catalegExcursions.getActivitatByName(nomExcursio, nomActivitat) == null) {
            return "Activitat no existent";
        }

        if (!actComprada(catalegExcursions.getActivitatByName(nomExcursio, nomActivitat), carteraSocis.find(nomSoci))) {
            return "L'activitat encara no ha estat comprada";
        }

        if (comentari.isEmpty()) {
            return "El text del comentari no pot ser buit";
        }

        Activitat a = catalegExcursions.getActivitatByName(nomExcursio, nomActivitat);
        Comentari c = new Comentari(nomSoci, comentari);
        a.addComentari(c);
        return "Comentari publicat";
    }

    public boolean actComprada(Activitat a, Soci s) {
        return s.findPagamentByAct(a) != null && s.findPagamentByAct(a).getEstaPagat();
    }

    public ArrayList<String> llistarComentaris(String nomExcursio, String nomActivitat) {
        return catalegExcursions.llistarComentaris(nomExcursio, nomActivitat);
    }

    public ArrayList<String> top10ActivitatsPerComentaris() {
        return catalegExcursions.top10ActivitatsPerComentaris();
    }

    /*-------------------------------------------------------------------------------------------------------------------------------------------
        APP PAYMENT STATS
     ------------------------------------------------------------------------------------------------------------------------------------------- */
    public String stats() {
        double bizum = 0, paypal = 0, cb = 0;
        for (Pagament p : pagamentsMap.values()) {
            if (p.getEstaPagat()) {
                if (p.getMetodePagament().nomMetode().equals("Bizum")) {
                    bizum++;
                }
                if (p.getMetodePagament().nomMetode().equals("Paypal")) {
                    paypal++;
                }
                if (p.getMetodePagament().nomMetode().equals("Compte Bancari")) {
                    cb++;
                }
            }
        }
        double total = bizum + paypal + cb;
        String s;
        s = "Estadistiques metodes de pagament: " + " Compte Bancari: " + toPercent(cb, total) + "% " + " Bizum: "
                + toPercent(bizum, total) + "% " + " Paypal: " + toPercent(paypal, total) + "%.";

        return s;
    }


    public String toPercent(double elems, double total) {
        double percent = (elems * 100) / total;
        DecimalFormat formato1 = new DecimalFormat("0.00");
        return formato1.format(percent);
    }
}