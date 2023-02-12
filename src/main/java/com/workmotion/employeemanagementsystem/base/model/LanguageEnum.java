package com.workmotion.employeemanagementsystem.base.model;

public enum LanguageEnum {
    EN("EN"),AR("AR"),GR_CASUAL("GR_CASUAL"),GR_STANDARD("GR_STANDARD");

    private String value;

    LanguageEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
