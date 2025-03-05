package br.com.fiap.semestral.service.clientesService;

public class ClientesServiceFactory {

    private ClientesServiceFactory() {
    }

    public static ClientesService create() {
        return new ClientesServiceImpl();
    }
}