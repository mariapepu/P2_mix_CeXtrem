package ub.edu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarteraSocis {
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
    private List<Soci> llista;

    public CarteraSocis() {
        iniCarteraSocis();
    }

    public void iniCarteraSocis() {
        List<Soci> listSocis = new ArrayList<>();
        listSocis.add(new Soci("ajaleo@gmail.com", "ajaleoPassw7"));
        listSocis.add(new Soci("dtomacal@yahoo.cat", "Qwertyft5"));
        listSocis.add(new Soci("heisenberg@gmail.com", "the1whoknocks"));
        listSocis.add(new Soci("rick@gmail.com", "wabalabadapdap22"));
        listSocis.add(new Soci("nietolopez10@gmail.com", "pekFD91m2a"));
        listSocis.add(new Soci("nancyarg10@yahoo.com", "contra10LOadc"));
        listSocis.add(new Soci("CapitaCC@gmail.com", "Alistar10"));
        listSocis.add(new Soci("nauin2@gmail.com", "kaynJGL20"));
        listSocis.add(new Soci("juancarlos999@gmail.com", "staIamsA12"));
        listSocis.add(new Soci("judit121@gmail.com", "Ordinador1"));

        llista = listSocis;
    }

    public Soci find(String username) {
        for (Soci c : llista) {
            if (c.getName().equals(username)) return c;
        }
        return null;
    }

    /*--------------------------
           REGISTRAR SOCI
    ----------------------------*/
    public boolean validatePassword(String b) {
        return passwordPattern.matcher(b).matches();
    }

    public boolean isMail(String correu) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correu);
        return matcher.find();
    }

    public int validateRegisterSoci(String username, String password) {
        if (find(username) != null) return -1;
        if (!validatePassword(password)) return -2;
        if (!isMail(username)) return -3;

        Soci s = new Soci(username, password);
        llista.add(s);
        return 0;

    }

    /*--------------------------
            LOGIN SOCI
     ---------------------------*/
    public String recuperarContrassenya(String username) {
        Soci soci = find(username);
        if (soci == null) {
            return "Correu inexistent";
        }
        return soci.getPwd();
    }

    public String loguejarSoci(String username, String password) {
        Soci soci = find(username);
        if (soci == null) {
            return "Correu inexistent";
        }
        if (soci.getPwd().equals(password)) {
            return "Login correcte";
        } else {
            return "Contrassenya incorrecta";
        }
    }

    /*--------------------------
            PAGAMENT
     ---------------------------*/
    public String addCompteBancari(String soci, String iban) {
        try {
            Soci s = find(soci);
            s.setCompteBancari(soci, iban);
            return "Compte afegit";
        } catch (Exception e) {
            return "No s'ha pogut afegir";
        }
    }


}
