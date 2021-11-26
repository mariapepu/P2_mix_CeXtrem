package ub.edu.model;

public class Comentari {

    private final String autor;
    private final String comentari;

    public Comentari(String autor, String comentari) {
        this.autor = autor;
        this.comentari = comentari;
    }

    public String getAutor() {
        return autor;
    }

    public String getComentari() {
        return comentari;
    }
}
