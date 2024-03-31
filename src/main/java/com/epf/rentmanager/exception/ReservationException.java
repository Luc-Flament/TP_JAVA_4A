package com.epf.rentmanager.exception;

public class ReservationException extends Exception{
    public ReservationException() {
        super();
    }

    // Constructeur avec un message d'erreur
    public ReservationException(String message) {
        super(message);
    }

    // Constructeur avec un message d'erreur et une cause
    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec une cause
    public ReservationException(Throwable cause) {
        super(cause);
    }
}
