package com.workmotion.employeemanagementsystem.base.exception.custom;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private Object[] params = null;
    private String detailedMessage;
    private String appCode;

    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message,String detailedMessage, String appCode) {
        super(message);
        this.detailedMessage = detailedMessage;
        this.appCode = appCode;
    }

    public BusinessException(String message, Object[] params) {
        super(message);
        this.params = params;
    }
}
