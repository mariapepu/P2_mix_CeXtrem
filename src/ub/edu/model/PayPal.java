package ub.edu.model;

public class PayPal implements MetodePagament {

    String mail;

    public PayPal(String mail) {
        this.mail = mail;
    }

    @Override
    public String pagar(Pagament p) {
        p.setEstaPagat(true);
        return "Excursio pagada amb exit";
    }

    @Override
    public String nomMetode() {
        return "Paypal";
    }

}
