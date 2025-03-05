package com.br.fiap.semestral.exceptions.veiculoException;

public class VeiculoNotSavedException extends Exception{
    public VeiculoNotSavedException(String message) {
        super(message);
    }

    public VeiculoNotSavedException(String message, Throwable cause){
        super(message, cause);
    }
}
