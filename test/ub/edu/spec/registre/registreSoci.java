package ub.edu.spec.registre;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;

@RunWith(ConcordionRunner.class)
public class registreSoci {
    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }


    public String getSoci(String a) {
        return controlador.findSoci(a);
    }

    public String validatePassword(String b) {
        return controlador.validatePassword(b);
    }

    public String validateUsername(String a) {
        return controlador.validateUsername(a);
    }

    public String isValidRegistre(String sociName, String password) {
        return controlador.validateRegisterSoci(sociName, password);
    }
}
