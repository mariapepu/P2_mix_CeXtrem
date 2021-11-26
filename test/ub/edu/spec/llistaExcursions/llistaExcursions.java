package ub.edu.spec.llistaExcursions;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;

@RunWith(ConcordionRunner.class)
public class llistaExcursions {
    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }

    public Iterable<String> listCatalegExcursionsPerNom() {
        return controlador.llistarCatalegExcursionsPerNom();
    }

    public Iterable<String> listCatalegExcursionsPerData() {
        return controlador.llistarCatalegExcursionsPerData();
    }

}