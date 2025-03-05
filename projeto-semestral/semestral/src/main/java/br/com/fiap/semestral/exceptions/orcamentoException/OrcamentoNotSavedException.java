package br.com.fiap.semestral.exceptions.orcamentoException;

public class OrcamentoNotSavedException extends Exception{
    public OrcamentoNotSavedException(String message) {
        super(message);
    }

    public OrcamentoNotSavedException(String message, Throwable cause){
        super(message, cause);
    }
}
