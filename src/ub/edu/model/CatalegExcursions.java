package ub.edu.model;

import ub.edu.resources.service.AbstractFactoryData;
import ub.edu.resources.service.DataService;
import ub.edu.resources.service.FactoryMOCK;

import java.util.*;

public class CatalegExcursions {
    private final DataService dataService;         // Connexio amb les dades
    private Map<String, Excursio> excursionsMap;
    private final Map<String, Especie> especiesMap;


    public CatalegExcursions(Map<String, Excursio> excursionsMap) {
        AbstractFactoryData factory = new FactoryMOCK();
        dataService = new DataService(factory);

        this.excursionsMap = excursionsMap;
        iniActivitatsMap();
        especiesMap = new HashMap<>();
    }

    public void iniActivitatsMap() {
        addActivitat("Delta de l'Ebre", "Ciclisme", (float) 35.4);
        addActivitat("Delta de l'Ebre", "Kayak", (float) 50);
        addActivitat("La Foradada", "Escalada", (float) 61.3);
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------------
                    EXCURSIO
    ------------------------------------------------------------------------------------------------------------------------------------------ */
    public List<Excursio> getExcursionsList() {
        return new ArrayList<>(excursionsMap.values());
    }

    public Excursio find(String e) {

        for (Excursio c : getExcursionsList()) {
            if (c.getNom().equals(e)) return c;
        }
        return null;

    }

    /*-------------------------------------------------------------------------------------------------------------------------------------------
                 LLISTAR EXCURSIONS
    ------------------------------------------------------------------------------------------------------------------------------------------ */

    public Iterable<String> llistarCatalegExcursionsPerNom() {
        SortedSet<String> excursionsDisponibles = new TreeSet<>();
        if (getExcursionsList().isEmpty()) {
            excursionsDisponibles.add("No hi ha excursions disponibles");
        } else {
            for (Excursio s : getExcursionsList()) {
                excursionsDisponibles.add(s.getNom());
            }
        }
        return excursionsDisponibles;
    }

    public Iterable<String> llistarCatalegExcursionsPerData() {
        List<Excursio> sortedList = getExcursionsList();
        sortedList.sort(Comparator.comparing(Excursio::getData));

        List<String> excursionsDisponibles = new ArrayList<>();
        for (Excursio s : sortedList) {
            excursionsDisponibles.add(s.getNom());
        }

        return excursionsDisponibles;
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------------------
                  BUSCAR EXCURSIONS
    ------------------------------------------------------------------------------------------------------------------------------------------------- */

    public Iterable<String> cercaExcursions() throws Exception {
        return dataService.cercaExcursions();
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------------
                     ACTIVITATS
    ------------------------------------------------------------------------------------------------------------------------------------------ */

    public void addActivitat(String nomExcursio, String nomActivitat, float preu) throws IllegalArgumentException {
        Excursio excursio = excursionsMap.get(nomExcursio);
        if (excursio == null) {
            throw new IllegalArgumentException("La excursio no existeix");
        }
        excursio.addActivitat(nomActivitat, preu);
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------------
                      ESPECIE
    ------------------------------------------------------------------------------------------------------------------------------------------- */

    public void afegirEspecieExcursio(String nomEspecie, String nomExcursio) {
        try {
            dataService.afegirEspecie(nomEspecie,nomExcursio);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*--------------------------------------------------------------------------------------------------------------------------------------------
                COMENTAR ACTIVITAT
    -------------------------------------------------------------------------------------------------------------------------------------------*/

    public Activitat
    getActivitatByName(String idExcursio, String titol) {
        Excursio e = this.find(idExcursio);
        HashMap<String, Activitat> activitats = (HashMap<String, Activitat>) e.getActivitatsMap();

        for (Activitat a : activitats.values()) {
            if (a.getNom().equals(titol)) return a;
        }
        return null;
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------------
                    LLISAR COMENTARIS
    --------------------------------------------------------------------------------------------------------------------------------------------*/

    public ArrayList<String> llistarComentaris(String nomExcursio, String nomActivitat) {
        ArrayList<String> comentarisOutput = new ArrayList<>();
        Excursio excursio = excursionsMap.getOrDefault(nomExcursio, null);
        if (excursio == null) {
            comentarisOutput.add("Excursio no existent");
            return comentarisOutput;
        }
        Activitat activitat = excursio.getActivitat(nomActivitat);
        if (activitat == null) {
            comentarisOutput.add("Activitat no existent");
            return comentarisOutput;
        }
        return (ArrayList<String>) activitat.llistarComentaris();
    }

    /*-------------------------------------------------------------------------------------------------------------------------------------------
                  TOP 10
    -------------------------------------------------------------------------------------------------------------------------------------------*/

    public ArrayList<String> top10ActivitatsPerComentaris() {
        ArrayList<String> top10 = new ArrayList<>();

        List<Activitat> activitats = new ArrayList<>();
        for (Excursio excursio : this.getExcursionsList()) {
            for (Activitat a : excursio.getActivitatsMap().values()) {
                if (!a.getComentaris().isEmpty()) {
                    activitats.add(a);
                }
            }
        }

        if (activitats.size() == 0) {
            top10.add("No hi ha activitats comentades");
            return top10;
        }

        activitats.sort((a1, a2) -> a2.getComentaris().size() - a1.getComentaris().size());
        activitats = activitats.subList(0, Integer.min(activitats.size(), 10));
        for (Activitat activitat : activitats) {
            String numComentaris;
            if (activitat.getComentaris().size() == 0) {
                numComentaris = "Sense";
            } else {
                numComentaris = Integer.toString(activitat.getComentaris().size());
            }

            top10.add(activitat.getNomExcursio() + " - " + activitat.getNom() + " - "
                    + numComentaris + " comentaris");
        }
        //top10 = excursio.t();


        return top10;
        //return excursions.top10ActivitatsPerComentaris(activitats);
    }

}