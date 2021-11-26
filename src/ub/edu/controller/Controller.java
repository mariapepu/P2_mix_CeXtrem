package ub.edu.controller;

import ub.edu.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    private CarteraSocis carteraSocis;   // Model
    private CatalegExcursions catalegExcursions;
    private Map<String, Pagament> pagamentsMap;


    public Controller() {

        this.catalegExcursions = new CatalegExcursions();
        this.carteraSocis = new CarteraSocis();
        iniPagamentsMap();
        pagamentsMap = new HashMap<>();

    }

    /*-----------------------------------------------------------------------------------------------------------------
            PAgament init
     ----------------------------------------------------------------------------------------------------------------*/

    public void iniPagamentsMap() {
        pagamentsMap = new HashMap<>();
        addPagament("Delta de l'Ebre", "Ciclisme", "001", "ajaleo@gmail.com", false);
        addPagament("Delta de l'Ebre", "Kayak", "002", "ajaleo@gmail.com", false);
        addPagament("La Foradada", "Escalada", "003", "ajaleo@gmail.com", true);
        addPagament("La Foradada", "Escalada", "005", "dtomacal@yahoo.cat", false);
        addPagament("Delta de l'Ebre", "Kayak", "006", "dtomacal@yahoo.cat", false);
    }

    private void addPagament(String e, String a, String id_transaccio, String soci, boolean estat) {
        Excursio exc = catalegExcursions.find(e);
        Activitat act = exc.getActivitat(a);
        pagamentsMap.put(id_transaccio, new Pagament(exc, act, id_transaccio, carteraSocis.find(soci), estat));
        Soci s = carteraSocis.find(soci);
        s.addPagament(id_transaccio, new Pagament(exc, act, id_transaccio, s, estat));
    }

    private List<Pagament> getPagamentList() {
        return new ArrayList<>(pagamentsMap.values());
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

        switch (esValid) {
            case -1:
                return "El nom ja existeix";
            case -2:
                return "Contrassenya no vàlida";
            case -3:
                return "Email incorrecte";

        }
        return "Soci creat correctament";
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
                return s.pagarCompteBancari(p);
            } else {
                return "L'excusió ja esta pagada";
            }
        } catch (Exception e) {
            return "No s'ha trobat el pagament";
        }
    }

    public String pagar(String soci, String id_pagament, MetodePagament mp) {
        Soci s = carteraSocis.find(soci);
        try {
            Pagament p = s.findPagament(id_pagament);
            if (!p.getEstaPagat()) {
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
        String str = String.valueOf(pagamentsMap.size());
        Pagament p = new Pagament(catalegExcursions.find(nomExc), catalegExcursions.getActivitatByName(nomExc, nomAct), str, s, false);
        s.addPagament(str, p);
        pagamentsMap.put(str, p);
    }

    /*------------------------------------------------------------------------------------------------------------------------------------------
        ESPECIE
     ---------------------------------------------------------------------------------------------------------------------------------------- */
    public Especie afegirEspecie(String nomEspecie) {
        return catalegExcursions.afegirEspecie(nomEspecie);
    }

    public void afegirEspecieExcursio(String nomEspecie, String nomExcursio) {
        catalegExcursions.afegirEspecieExcursio(nomEspecie, nomExcursio);
    }

    public Iterable<String> cercaExcursions() {
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
        if (s.findPagamentByAct(a) == null || s.findPagamentByAct(a).getEstaPagat() == false) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<String> llistarComentaris(String nomExcursio, String nomActivitat) {
        return catalegExcursions.llistarComentaris(nomExcursio, nomActivitat);
    }

    public ArrayList<String> top10ActivitatsPerComentaris() {
        return catalegExcursions.top10ActivitatsPerComentaris();
    }

}
