package ub.edu.resources.dao.MOCK;

import ub.edu.model.Soci;
import ub.edu.resources.dao.DAOSoci;

import java.util.*;

public class DAOSociMOCK implements DAOSoci {

    private final Map<String, Soci> listSocis = new HashMap<>();

    public DAOSociMOCK() {
        listSocis.put("ajaleo@gmail.com", new Soci("ajaleo@gmail.com", "ajaleoPassw7"));
        listSocis.put("dtomacal@yahoo.cat", new Soci("dtomacal@yahoo.cat", "Qwertyft5"));
        listSocis.put("heisenberg@gmail.com", new Soci("heisenberg@gmail.com", "the1whoknocks"));
        listSocis.put("rick@gmail.com",  new Soci("rick@gmail.com", "wabalabadapdap22"));
        listSocis.put("nietolopez10@gmail.com",new Soci("nietolopez10@gmail.com", "pekFD91m2a"));
        listSocis.put("nancyarg10@yahoo.com", new Soci("nancyarg10@yahoo.com", "contra10LOadc"));
        listSocis.put( "CapitaCC@gmail.com",  new Soci("CapitaCC@gmail.com", "Alistar10"));
        listSocis.put( "nauin2@gmail.com", new Soci("nauin2@gmail.com", "kaynJGL20"));
        listSocis.put( "juancarlos999@gmail.com", new Soci("juancarlos999@gmail.com", "staIamsA12"));
        listSocis.put( "judit121@gmail.com", new Soci("judit121@gmail.com", "Ordinador1"));
    }

    @Override
    public List<Soci> getAll() {
        return new ArrayList<>(listSocis.values());
    }

    @Override
    public Optional<Soci> getById(String id) {
        return Optional.ofNullable(listSocis.get(id));
    }

    @Override
    public boolean add(final Soci soci) {
        if (listSocis.containsKey(soci.getName())) {
            return false;
        }
        listSocis.put(soci.getName(), soci);
        return true;
    }

    @Override
    public boolean update(final Soci soci, String[] params) {
        soci.setName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        soci.setPwd(Objects.requireNonNull(
                params[1], "Password cannot be null"));
        return listSocis.replace(soci.getName(), soci) != null;
    }

    @Override
    public boolean delete(final Soci soci) {
        return listSocis.remove(soci.getName()) != null;
    }

    @Override
    public Soci findSociByUserNameAndPassword(String usuari, String pwd) throws Exception {
        if (getById(usuari).isPresent()) {
            Soci c = listSocis.get(usuari);
            if (c.getPwd().equals(pwd)) {
                return c;
            } else throw new Exception();
        } else throw new Exception();
    }

}
