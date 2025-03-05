package br.com.fiap.semestral.DAO.agendaDAO;


public class AgendaDaoFactory {

    public static AgendaDao create(){
        return new AgendaDAOImpl();
    }
}
