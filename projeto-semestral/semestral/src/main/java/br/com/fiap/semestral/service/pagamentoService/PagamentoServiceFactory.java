package com.br.fiap.semestral.service.pagamentoService;

public class PagamentoServiceFactory {

    public static PagamentoService create(){
        return new PagamentoServiceImpl();
    }
}
