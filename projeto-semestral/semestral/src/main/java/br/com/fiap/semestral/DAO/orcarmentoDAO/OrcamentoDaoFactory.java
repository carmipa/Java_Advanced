package br.com.fiap.semestral.DAO.orcarmentoDAO;

public class OrcamentoDaoFactory {

    public static OrcamentoDao create(){
        return new OrcamentoDAOImpl();
    }
}
