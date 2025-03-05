package br.com.fiap.semestral.DAO.oficinaDAO;

public class OficinaDaoFactory {

    public static OficinaDao create(){
        return new OficinaDAOImpl();
    }
}
