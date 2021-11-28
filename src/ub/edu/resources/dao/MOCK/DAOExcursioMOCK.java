package ub.edu.resources.dao.MOCK;

import ub.edu.model.Excursio;
import ub.edu.resources.dao.DAOExcursio;

import java.util.*;

public class DAOExcursioMOCK implements DAOExcursio {

    private final Map<String, Excursio> idexcursio = new HashMap<>();

    public DAOExcursioMOCK() {
        addExcursio("Museu Miró", "29/09/2021");
        addExcursio( "La Foradada", "04/10/2021");
        addExcursio( "El camí des Correu", "10/10/2021");
        addExcursio("Delta de l'Ebre", "11/10/2021");
        addExcursio("El PedraForca", "13/10/2021");
        addExcursio("Colònia Güell", "22/10/2021");
        addExcursio("Castell de Cardona", "24/10/2021");
        addExcursio("Aiguamolls de l'Empordà", "27/10/2021");
        addExcursio("Cap de Creus i Cadaqués", "01/11/2021");
        addExcursio("Aigüestortes i Sant Maurici", "03/11/2021");
    }

    private void addExcursio(String nom, String dataText){
        idexcursio.put(nom, new Excursio(nom, dataText));
    }



    @Override
    public List<Excursio> getAll() {
        return new ArrayList<>(idexcursio.values());
    }

    @Override
    public Optional<Excursio> getById(String id) {
        return Optional.ofNullable(idexcursio.get(id));
    }

    @Override
    public boolean add(final Excursio excursio) {

        if (getById(excursio.getNom()).isPresent()) {
            return false;
        }
        idexcursio.put(excursio.getNom(), excursio);
        return true;
    }

    @Override
    public boolean update(final Excursio excursio, String[] params) {
        excursio.setNom(Objects.requireNonNull(
                params[0], "Title cannot be null"));
        return idexcursio.replace(excursio.getNom(), excursio) != null;
    }

    @Override
    public boolean delete(final Excursio excursio) {
        return idexcursio.remove(excursio.getNom()) != null;
    }
}
