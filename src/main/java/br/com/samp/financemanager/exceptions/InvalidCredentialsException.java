package br.com.samp.financemanager.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(){
        super("Invalid email or password");
    }
}
