package ub.edu.spec.cercaExcursions;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;

@RunWith(ConcordionRunner.class)
public class cercaExcursions {

    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }

    public Iterable<String> cercaExcursions() {
        return controlador.cercaExcursions();
    }

    public void afegirEspecies() {
        controlador.afegirEspecieExcursio("Llúdriga", "Museu Miró");
        controlador.afegirEspecieExcursio("Llúdriga", "La Foradada");
        controlador.afegirEspecieExcursio("Llúdriga", "El camí des Correu");
        controlador.afegirEspecie("Milà Reial");
        controlador.afegirEspecieExcursio("Turó europeu", "Museu Miró");
        controlador.afegirEspecieExcursio("Turó europeu", "La Foradada");
    }

}
