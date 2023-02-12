package com.workmotion.employeemanagementsystem.base.exception.custom;

public class ResourceNotFoundException extends RuntimeException {
    private Object[] params = null;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Object[] params) {
        super(message);
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
