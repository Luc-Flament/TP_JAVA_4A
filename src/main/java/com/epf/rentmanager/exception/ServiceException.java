package com.epf.rentmanager.exception;

public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }

    // Constructeur avec un message d'erreur
    public ServiceException(String message) {
        super(message);
    }

    // Constructeur avec un message d'erreur et une cause
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec une cause
    public ServiceException(Throwable cause) {
        super(cause);
    }
}