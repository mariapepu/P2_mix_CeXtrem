package ub.edu.model;

public class Bizum implements MetodePagament {
    int telf_desti;
    float valor;

    public Bizum(float valor) {
        this.valor = valor;
    }

    public String pagar(Pagament p) {
        p.setEstaPagat(true);
        return "Excursio pagada amb exit";
    }

    @Override
    public String nomMetode() {
        return "Bizum";
    }
}
