package ub.edu.spec.llistaPagament;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;

@RunWith(ConcordionRunner.class)
public class llistaPagaments {
    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }

    public String listPagamenmtsSoci(String soci) {
        return controlador.llistarPagaments(soci);
    }
}
