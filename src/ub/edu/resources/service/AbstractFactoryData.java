package ub.edu.resources.service;

import ub.edu.resources.dao.DAOEspecie;
import ub.edu.resources.dao.DAOExcursio;
import ub.edu.resources.dao.DAOSoci;

public interface AbstractFactoryData {
     DAOSoci createDAOSoci();
     DAOExcursio createDAOExcursio();
     DAOEspecie createDAOEspecie();
}
