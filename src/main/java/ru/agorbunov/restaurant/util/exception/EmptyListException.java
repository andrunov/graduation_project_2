package ru.agorbunov.restaurant.util.exception;

/**
 * This exception must be thrown in case of occurrence empty collection or array,
 * in a case if this is unacceptable of logic program
 */
public class EmptyListException extends RuntimeException{
    public EmptyListException(String message) {
        super(message);
    }
}
