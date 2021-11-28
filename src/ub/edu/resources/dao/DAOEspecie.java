package ub.edu.resources.dao;

import ub.edu.model.Especie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DAOEspecie extends DAO<Especie>
{
    Map<Especie, ArrayList<String>> getLlistaEspecies();
    List<String> getExcursionsForEspecie(Especie especie) throws Exception;
    void setValue (Especie e,String s);
    void afegirEspecie(Especie e, String s);
    boolean inEspecies (Especie e);
}
