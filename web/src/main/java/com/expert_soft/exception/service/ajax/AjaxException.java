package com.expert_soft.exception.service.ajax;


public class AjaxException extends RuntimeException {

    public AjaxException() {}

    public AjaxException(String message) {
        super(message);
    }

    public AjaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public AjaxException(Throwable cause) {
        super(cause);
    }
}
