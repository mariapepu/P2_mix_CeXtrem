package ub.edu.resources.service;

import ub.edu.resources.dao.*;
import ub.edu.resources.dao.MOCK.*;

public class FactoryMOCK implements AbstractFactoryData {

    @Override
    public DAOSoci createDAOSoci() {
        return new DAOSociMOCK();
    }

    @Override
    public DAOExcursio createDAOExcursio() {
        return new DAOExcursioMOCK();
    }

    @Override
    public DAOEspecie createDAOEspecie() {
        return new DAOEspecieMOCK();
    }

    // TO DO crear els altres DAOs de les altres classes
}
