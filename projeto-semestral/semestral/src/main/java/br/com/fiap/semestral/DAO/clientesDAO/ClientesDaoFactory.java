package br.com.fiap.semestral.DAO.clientesDAO;

public class ClientesDaoFactory {

    public static ClientesDao create(){
        return new ClientesDaoImpl();
    }
}
