package com.epf.rentmanager.exception;

public class DaoException extends Exception{
    public DaoException() {
        super();
    }

    // Constructeur avec un message d'erreur
    public DaoException(String message) {
        super(message);
    }

    // Constructeur avec un message d'erreur et une cause
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec une cause
    public DaoException(Throwable cause) {
        super(cause);
    }
}
