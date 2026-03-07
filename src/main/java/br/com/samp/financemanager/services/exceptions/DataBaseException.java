package br.com.samp.financemanager.services.exceptions;

public class DataBaseException extends BusinessException {
    public DataBaseException(String message) {
        super("database:" + message);
    }
}
