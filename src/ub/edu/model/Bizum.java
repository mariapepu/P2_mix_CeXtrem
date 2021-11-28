package ub.edu.model;

public class Bizum implements MetodePagament {
    String telf_desti;

    public Bizum(String telf_desti) {
        this.telf_desti = telf_desti;
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
