package ub.edu.model;

import java.util.ArrayList;
import java.util.List;

public class Activitat {

    private final String nomExcursio;
    private final String nom;
    private final float preu;
    private final ArrayList<Comentari> comentaris;

    public Activitat(String nomExcursio, String nom, float preu) {
        this.nomExcursio = nomExcursio;
        this.nom = nom;
        this.preu = preu;
        this.comentaris = new ArrayList<>();
    }

    public String getNomExcursio() {
        return nomExcursio;
    }

    public String getNom() {
        return nom;
    }

    public float getPreu() {
        return preu;
    }

    public ArrayList<Comentari> getComentaris() {
        return comentaris;
    }

    public void addComentari(Comentari comentari) {
        comentaris.add(comentari);
    }

    public List<String> llistarComentaris() {
        List<String> comentariList = new ArrayList<>();
        for (Comentari c : comentaris) {
            comentariList.add(c.getAutor() + ": " + c.getComentari());
        }
        if (comentariList.size() == 0) {
            comentariList.add("No hi ha comentaris");
        }
        return comentariList;
    }


}
