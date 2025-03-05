package br.com.fiap.semestral.service.orcamentoService;


public class OrcamentoServiceFactory {

    public static OrcamentoService create(){
        return new OrcamentoServiceImpl();
    }



}
