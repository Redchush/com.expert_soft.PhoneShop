package com.expert_soft.exception.service;


public class NoSuchEntityException extends ServiceException {


    private String failedEntity;

    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }

    public NoSuchEntityException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getFailedEntity() {
        return failedEntity;
    }

    public void setFailedEntity(String failedEntity) {
        this.failedEntity = failedEntity;
    }
}
