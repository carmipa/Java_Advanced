package br.com.fiap.semestral.exceptions.pecasException;

public class PecasNotFoundException extends Exception{
    public PecasNotFoundException(String message) {
        super(message);
    }

    public PecasNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
