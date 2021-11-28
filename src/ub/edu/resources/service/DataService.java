package ub.edu.resources.service;

import ub.edu.model.Especie;
import ub.edu.model.Excursio;
import ub.edu.model.Pagament;
import ub.edu.model.Soci;
import ub.edu.resources.dao.DAOEspecie;
import ub.edu.resources.dao.DAOExcursio;
import ub.edu.resources.dao.DAOSoci;

import java.util.*;

public class DataService {
    private final DAOExcursio daoExcursio;
    private final DAOSoci daoSoci;
    private final DAOEspecie daoEspecie;


    public DataService(AbstractFactoryData factory){

        this.daoSoci = factory.createDAOSoci();
        this.daoExcursio = factory.createDAOExcursio();
        this.daoEspecie = factory.createDAOEspecie();
        // TO DO: Crear els altres DAO de les altres estructures
    }

    //-----------------------------------------------------------------------
    //socis

    public Optional<Soci> getSociByUsername(String usuari) {
        try {
            return daoSoci.getById(usuari);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return Optional.empty();
    }

    public List<Soci> getAllSocis() throws Exception {
        return daoSoci.getAll();
    }

    //-----------------------------------------------------------------------
    //excursions

    public List<Excursio> getAllExcursions() throws Exception {
        return daoExcursio.getAll();
    }

    public Iterable<String> cercaExcursions() throws Exception {
        SortedSet<String> especies = new TreeSet<>();
        Map<Especie, ArrayList<String>> especiesMap = getLlistaEspecies();
        if (especiesMap.isEmpty()){
            especies.add("No hi han espècies enregistrades");
            return especies;
        } else {
            especiesMap.size();
        }

        for (Especie especie : especiesMap.keySet()){
            especies.add(especie.getNom() + " " + daoEspecie.getExcursionsForEspecie(especie).size());
        }

        return especies;
    }

    //-----------------------------------------------------------------------
    //especies

    public Map<Especie, ArrayList<String>> getLlistaEspecies() {
        return daoEspecie.getLlistaEspecies();
    }

    public void afegirEspecie(String nomEspecie, String exc) throws Exception {
        Especie e = new Especie(nomEspecie);

        if (daoEspecie.inEspecies(e)){
            daoEspecie.setValue(e, exc);
        }else {
            daoEspecie.afegirEspecie(e, exc);
        }

        if(daoExcursio.getById(exc).isPresent()) {
            daoExcursio.getById(exc).get().addEspecie(e);
        }

    }
    //------------------------------------------------------------------------
    //pagar


}