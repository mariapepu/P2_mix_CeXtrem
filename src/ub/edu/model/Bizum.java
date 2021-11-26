package ub.edu.model;

public class Bizum implements MetodePagament {
    int telf_desti;
    float valor;
    String mail_desti;
    boolean realitzat;

    public Bizum(float valor) {

        this.valor = valor;
    }

    public String pagar(Pagament p) {
        p.setEstaPagat(true);
        return "Excursio pagada amb exit";
    }
}
