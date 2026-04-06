package br.com.samp.financemanager.exceptions;

public class AccountDisableException extends RuntimeException {
    public AccountDisableException(String message) {
        super(message);
    }

    public AccountDisableException() {
        super("Account disabled");
    }
}
