    package br.com.samp.financemanager.exceptions;

    public class DataBaseException extends RuntimeException {
        public DataBaseException(String message) {
            super("database:" + message);
        }
    }
