package ub.edu.spec.AddCompteBanc;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;

@RunWith(ConcordionRunner.class)
public class AddCompteBanc {
    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }

    public String addCompteBanc(String soci, String iban){
        return controlador.addCompteBanc(soci, iban);
    }

}


