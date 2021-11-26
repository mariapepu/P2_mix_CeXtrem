package ub.edu.spec.login;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;

@RunWith(ConcordionRunner.class)
public class loginSoci {
    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }

    public String recuperarContrassenya(String user) {
        return controlador.recuperarContrassenya(user);
    }

    public String loginSoci(String user, String pass) {
        return controlador.loguejarSoci(user, pass);
    }

}
