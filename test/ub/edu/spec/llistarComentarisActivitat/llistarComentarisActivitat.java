package ub.edu.spec.llistarComentarisActivitat;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;

import java.util.ArrayList;

@RunWith(ConcordionRunner.class)
public class llistarComentarisActivitat {

    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }

    public void initComentaris(String excursio, String activitat) {
        String[][] comentaris = {
                {"ajaleo@gmail.com", "Molt divertit!"},
                {"judit121@gmail.com", "Increible"},
                {"heisenberg@gmail.com", "Una mica esgotador..."}
        };
        for (String[] comentari : comentaris) {
            controlador.addCompteBanc(comentari[0], "ES66 6666 6666 6666");
            controlador.reservarActivitat(comentari[0], excursio, activitat);
            controlador.pagarCompteBanc(comentari[0], excursio, activitat);
            controlador.comentarActivitat(comentari[0], excursio, activitat, comentari[1]);
        }
    }

    public ArrayList<String> llistarComentaris(String excursio, String activitat) {
        return controlador.llistarComentaris(excursio, activitat);
    }
}
