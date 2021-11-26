package ub.edu.spec.comentarActivitat;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;

@RunWith(ConcordionRunner.class)
public class comentarActivitat {

    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }

    public String comentarActivitat(String user, String excursio, String activitat, String comentari) {
        return controlador.comentarActivitat(user, excursio, activitat, comentari);
    }
}
