package com.br.fiap.semestral.DAO.agendaDAO;


public class AgendaDaoFactory {

    public static AgendaDao create(){
        return new AgendaDAOImpl();
    }
}
