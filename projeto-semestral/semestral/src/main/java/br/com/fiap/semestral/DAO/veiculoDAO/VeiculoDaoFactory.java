package com.br.fiap.semestral.DAO.veiculoDAO;

public class VeiculoDaoFactory {

    public static VeiculoDao create(){
        return new VeiculoDAOImpl();
    }
}
