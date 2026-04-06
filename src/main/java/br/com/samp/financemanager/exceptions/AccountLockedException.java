package br.com.samp.financemanager.exceptions;

import lombok.Getter;

@Getter
public class AccountLockedException extends RuntimeException {

    private final long retryAfterSeconds;

    public AccountLockedException(String message, long retryAfterSeconds){
        super(message);
        this.retryAfterSeconds = retryAfterSeconds;
    }

    public AccountLockedException(long retryAfterSeconds){
        super("Too Many Requests,account locked. Please try again later");
        this.retryAfterSeconds = retryAfterSeconds;
    }


}
