package br.com.fiap.semestral.service.pagamentoService;

public class PagamentoServiceFactory {

    public static PagamentoService create(){
        return new PagamentoServiceImpl();
    }
}
