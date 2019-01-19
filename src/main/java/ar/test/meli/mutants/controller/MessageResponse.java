package ar.test.meli.mutants.controller;

class MessageResponse {

    static final String SUCCESS_STATUS = "success";
    static final String ERROR_STATUS = "error";

    private final String status;
    private String message;

    static MessageResponse success(String message) {
        return new MessageResponse(SUCCESS_STATUS, message);
    }

    static MessageResponse error(String message) {
        return new MessageResponse(ERROR_STATUS, message);
    }

    private MessageResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
