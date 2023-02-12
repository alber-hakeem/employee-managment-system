package com.workmotion.employeemanagementsystem.base.api.request;


public enum SortingDirection {
    ASC("ASC"),
    DESC("DESC");
    private String value;
    public String getValue() { return value; }
    SortingDirection(String value){
        this.value = value;
    }
}
