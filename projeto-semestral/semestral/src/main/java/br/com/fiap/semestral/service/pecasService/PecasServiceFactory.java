package br.com.fiap.semestral.service.pecasService;

public class PecasServiceFactory {

    public static PecasService create(){
        return new PecasServiceImpl();
    }
}
