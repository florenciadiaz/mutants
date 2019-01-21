package ar.test.meli.mutants.model.exception;

public abstract class BusinessException extends Exception {

    BusinessException(String message) {
        super(message);
    }
}
