package com.br.fiap.semestral.service.veiculoService;

public class VeiculoServiceFactory {

    public static VeiculoService create(){
        return new VeiculoServiceImpl();
    }


}
