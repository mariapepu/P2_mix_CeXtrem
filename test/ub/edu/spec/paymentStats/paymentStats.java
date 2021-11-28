package ub.edu.spec.paymentStats;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;
import ub.edu.model.Bizum;
import ub.edu.model.CompteBancari;
import ub.edu.model.PayPal;

@RunWith(ConcordionRunner.class)
public class paymentStats {

    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }
    public String stats() {
        return controlador.stats();
    }

    //exemple 2
    public void mesPagaments(){ //totalpagaments=6, b=3, p=1, cb=2(1+el nou)
        //3 pagaments amb bizum
        controlador.reservarActivitat("rick@gmail.com", "Delta de l'Ebre", "Ciclisme");
        controlador.pagar("rick@gmail.com", "Delta de l'Ebre", "Ciclisme",new Bizum("666 666 666"));
        controlador.reservarActivitat("rick@gmail.com", "Delta de l'Ebre", "Kayak");
        controlador.pagar("rick@gmail.com", "Delta de l'Ebre", "Kayak",new Bizum("666 666 666"));
        controlador.reservarActivitat("rick@gmail.com", "La Foradada", "Escalada");
        controlador.pagar("rick@gmail.com", "La Foradada", "Escalada",new Bizum("666 666 666"));

        //1 pagaments amb compte bancari
        controlador.reservarActivitat("nauin2@gmail.com", "Delta de l'Ebre", "Kayak");
        controlador.pagar("nauin2@gmail.com", "Delta de l'Ebre", "Kayak",new CompteBancari("nauin2@gmail.com","ES66 6666 6666 6666"));

        //1 pagaments amb paypal
        controlador.reservarActivitat("nauin2@gmail.com", "La Foradada", "Escalada");
        controlador.pagar("nauin2@gmail.com", "La Foradada", "Escalada",new PayPal("nauin2@gmail.com"));
    }
}
