package ub.edu.spec.top10ActivitatsPerComentaris;

import org.concordion.api.BeforeExample;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import ub.edu.controller.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RunWith(ConcordionRunner.class)
public class top10ActivitatsPerComentaris {

    private Controller controlador;

    @BeforeExample
    private void init() {
        controlador = new Controller();
    }

    private void initComentaris(String excursio, String activitat, String[][] comentaris) {
        for (String[] comentari : comentaris) {
            controlador.addCompteBanc(comentari[0], "ES66 6666 6666 6666");
            controlador.reservarActivitat(comentari[0], excursio, activitat);
            controlador.pagarCompteBanc(comentari[0], excursio, activitat);
            controlador.comentarActivitat(comentari[0], excursio, activitat, comentari[1]);
        }
    }

    public void initPoquesActivitats() {
        String[][] activitats = {
                {"Delta de l'Ebre", "Kayak"},
                {"Delta de l'Ebre", "Ciclisme"},
                {"Delta de l'Ebre", "Natacio"},
                {"La Foradada", "Escalada"},
        };
        double[] preus = {35.4, 50., 61.3, 3.2};
        for (int i = 0; i < preus.length; i++) {
            controlador.addActivitat(activitats[i][0], activitats[i][1], (float) preus[i]);
        }

        String[][] comentaris1 = {
                {"ajaleo@gmail.com", "Molt divertit!"},
                {"dtomacal@yahoo.cat", "Increible"},
                {"heisenberg@gmail.com", "Una mica esgotador..."}
        };
        initComentaris(activitats[1][0], activitats[1][1], comentaris1);

        String[][] comentaris3 = {
                {"ajaleo@gmail.com", "Molt divertit!!!"},
        };
        initComentaris(activitats[3][0], activitats[3][1], comentaris3);
    }

    public void initMoltesActivitats() {
        initPoquesActivitats();

        String[][] activitats = {
                {"Cap de Creus i Cadaqués", "Museu Casa Dali"},
                {"Aiguamolls de l'Empordà", "Anellament d'ocells"},
                {"Colònia Güell", "Espectacle"},
                {"Castell de Cardona", "Mercat medieval"},
                {"Colònia Güell", "Visita guiada"},
                {"Castell de Cardona", "Visita guiada"},
                {"El camí des Correu", "Ciclisme"},
                {"El camí des Correu", "Mitja marato"},
                {"El camí des Correu", "Visita Can Joan"},
        };
        double[] preus = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < preus.length; i++) {
            controlador.addActivitat(activitats[i][0], activitats[i][1], (float) preus[i]);
        }

        int[] numComentaris = {12, 11, 9, 7, 7, 4, 2, 2, 2};
        ArrayList<String> socis = new ArrayList<String>(List.of("ajaleo@gmail.com", "dtomacal@yahoo.cat", "heisenberg@gmail.com"));
        for (int i = 0; i < numComentaris.length; i++) {
            for (int j = 0; j < numComentaris[i]; j++) {
                Collections.shuffle(socis);
                String comentari = UUID.randomUUID().toString();
                controlador.addCompteBanc(socis.get(0), "ES66 6666 6666 6666");
                controlador.reservarActivitat(socis.get(0), activitats[i][0], activitats[i][1]);
                controlador.pagarCompteBanc(socis.get(0), activitats[i][0], activitats[i][1]);
                controlador.comentarActivitat(socis.get(0), activitats[i][0], activitats[i][1], comentari);
            }
        }
    }

    /*public void initUser(String nomSoci) {
        controlador.afegirBizum(nomSoci, "666 666 666");
        controlador.pagarActivitat(nomSoci, "Delta de l'Ebre", "Ciclisme");
        controlador.pagarActivitat(nomSoci, "La Foradada", "Escalada");
        controlador.afegirCompteBancari(nomSoci, "ES66 6666 6666 6666");
        controlador.pagarActivitat(nomSoci, "Delta de l'Ebre", "Kayak");
    }
*/
    public ArrayList<String> visualitzaTop10ActivitatsPerComentaris() {
        return controlador.top10ActivitatsPerComentaris();
    }
}
