package ub.edu.model;

import java.util.*;

public class CatalegExcursions {
    private Map<String, Excursio> excursionsMap;
    private final Map<String, Especie> especiesMap;


    public CatalegExcursions() {
        iniExcursionsMap();
        iniActivitatsMap();
        especiesMap = new HashMap<>();
    }

    public void iniExcursionsMap() {
        excursionsMap = new HashMap<>();
        addExcursio("Museu Miró", "29/09/2021");
        addExcursio("La Foradada", "04/10/2021");
        addExcursio("El camí des Correu", "10/10/2021");
        addExcursio("Delta de l'Ebre", "11/10/2021");
        addExcursio("El PedraForca", "13/10/2021");
        addExcursio("Colònia Güell", "22/10/2021");
        addExcursio("Castell de Cardona", "24/10/2021");
        addExcursio("Aiguamolls de l'Empordà", "27/10/2021");
        addExcursio("Cap de Creus i Cadaqués", "01/11/2021");
        addExcursio("Aigüestortes i Sant Maurici", "03/11/2021");
    }

    public void iniActivitatsMap() {
        addActivitat("Delta de l'Ebre", "Ciclisme", (float) 35.4);
        addActivitat("Delta de l'Ebre", "Kayak", (float) 50);
        addActivitat("La Foradada", "Escalada", (float) 61.3);
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------------
                    EXCURSIO
    ------------------------------------------------------------------------------------------------------------------------------------------ */

    private void addExcursio(String nom, String dataText) {
        excursionsMap.put(nom, new Excursio(nom, dataText));
    }

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

    public Iterable<String> cercaExcursions() {
        SortedSet<String> especies = new TreeSet<>();

        if (especiesMap.size() == 0) {
            especies.add("No hi han espècies enregistrades");
            return especies;
        }

        for (Especie especie : especiesMap.values()) {
            especies.add(especie.getNom() + " " + comptarExcursionsEspecie(especie));
        }

        return especies;
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

    public Especie afegirEspecie(String nomEspecie) {
        Especie especie;
        if (especiesMap.containsKey(nomEspecie)) {
            especie = especiesMap.get(nomEspecie);
        } else {
            especie = new Especie(nomEspecie);
            especiesMap.put(nomEspecie, especie);
        }
        return especie;
    }

    public void afegirEspecieExcursio(String nomEspecie, String nomExcursio) {
        Especie especie = afegirEspecie(nomEspecie);
        excursionsMap.get(nomExcursio).addEspecie(especie);

    }

    private int comptarExcursionsEspecie(Especie especie) {
        int count = 0;
        for (Excursio excursio : excursionsMap.values()) {
            if (excursio.containsEspecie(especie)) count++;
        }
        return count;
    }

    /*--------------------------------------------------------------------------------------------------------------------------------------------
                COMENTAR ACTIVITAT
    -------------------------------------------------------------------------------------------------------------------------------------------*/

    public Activitat getActivitatByName(String idExcursio, String titol) {
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