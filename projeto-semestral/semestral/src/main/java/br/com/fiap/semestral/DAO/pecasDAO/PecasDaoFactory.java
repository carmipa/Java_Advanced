package com.br.fiap.semestral.DAO.pecasDAO;

public class PecasDaoFactory {

    public static PecasDao create(){
        return new PecasDAOImpl();
    }
}
