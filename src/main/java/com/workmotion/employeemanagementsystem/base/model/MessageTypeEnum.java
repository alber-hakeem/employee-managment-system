package com.workmotion.employeemanagementsystem.base.model;

public enum MessageTypeEnum {
    FRONT_END("FRONT_END"),
    BACK_END("BACK_END"),
    ADMIN_APP("ADMIN_APP");

    private String type;

    MessageTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
