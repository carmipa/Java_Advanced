package com.br.fiap.semestral.exceptions.orcamentoException;

public class OrcamentoNotFoundException extends Exception{
    public OrcamentoNotFoundException(String message) {
        super(message);
    }

    public OrcamentoNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
