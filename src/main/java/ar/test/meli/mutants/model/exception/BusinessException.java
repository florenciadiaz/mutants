package ar.test.meli.mutants.model.exception;

abstract class BusinessException extends Exception {

    BusinessException(String message) {
        super(message);
    }
}
