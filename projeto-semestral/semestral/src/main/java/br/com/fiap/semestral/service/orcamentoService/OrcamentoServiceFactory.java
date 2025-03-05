package com.br.fiap.semestral.service.orcamentoService;


public class OrcamentoServiceFactory {

    public static OrcamentoService create(){
        return new OrcamentoServiceImpl();
    }



}
