package br.com.samp.financemanager.services.exceptions;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Object id) {
        super("Resource not found with id: " + id);
    }
}
