package ub.edu.spec.pagarActivitat;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;
import ub.edu.model.Bizum;
import ub.edu.model.PayPal;

@RunWith(ConcordionRunner.class)
public class pagarActivitat {
    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }

    public String pagarAmbCompte(String user, String exc, String act) {
        //primer afegim el compte bancari de ajaleo, ja que si no el cas en que tot ba be no funcionaria
        controlador.addCompteBanc("ajaleo@gmail.com", "ES66 6666 6666 6666");
        return controlador.pagarCompteBanc(user, exc, act);
    }

    public String pagarAmbBizum(String user, String pagament) {
        return controlador.pagar(user, pagament, new Bizum(10.6f));
    }

    public String pagarAmbPaypal(String user, String pagament) {
        return controlador.pagar(user, pagament, new PayPal(user));
    }

}
