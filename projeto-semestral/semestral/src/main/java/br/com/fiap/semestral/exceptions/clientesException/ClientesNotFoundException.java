package com.br.fiap.semestral.exceptions.clientesException;

public class ClientesNotFoundException extends Exception {


    public ClientesNotFoundException(String message) {
        super(message);
    }

    public ClientesNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
