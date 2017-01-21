package com.expert_soft.exception.service;


public class NotUniqueEntityException extends ServiceException {

    public NotUniqueEntityException() {
    }

    public NotUniqueEntityException(String message) {
        super(message);
    }

    public NotUniqueEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueEntityException(Throwable cause) {
        super(cause);
    }

    public NotUniqueEntityException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
