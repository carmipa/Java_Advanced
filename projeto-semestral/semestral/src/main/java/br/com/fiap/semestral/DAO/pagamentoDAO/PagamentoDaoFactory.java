package br.com.fiap.semestral.DAO.pagamentoDAO;

public class PagamentoDaoFactory {

    public static PagamentoDao create(){
        return new PagamentoDAOImpl();
    }
}
