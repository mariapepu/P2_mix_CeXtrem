package ub.edu.resources.dao;

import ub.edu.model.Soci;

public interface DAOSoci extends DAO<Soci> {
    Soci findSociByUserNameAndPassword(String usuari, String pwd) throws Exception;
}
