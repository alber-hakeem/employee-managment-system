package com.workmotion.employeemanagementsystem.base.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class SortingBy {
    private String fieldName;
    private SortingDirection direction=SortingDirection.ASC;
    private Boolean isNumber=false;
    public SortingBy(String fieldName,SortingDirection direction){
        this.direction=direction;
        this.fieldName=fieldName;
    }
}
