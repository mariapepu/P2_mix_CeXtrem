package ub.edu.model;

public class CompteBancari implements MetodePagament {
    //atributs
    String iban;
    String titular;

    public CompteBancari(String titular, String iban) {
        this.titular = titular;
        this.iban = iban;
    }

    public String pagar(Pagament p) {
        p.setEstaPagat(true);
        return "Excursio pagada amb exit";
    }

    @Override
    public String nomMetode() {
        return "Compte Bancari";
    }
}