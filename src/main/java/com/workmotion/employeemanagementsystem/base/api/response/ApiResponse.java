package com.workmotion.employeemanagementsystem.base.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiResponse<T> {
    private String appCode;
    private String message; // user friendly message
    private String detailedMessage; // debug message
    private Map<String, Set<String>> errors;
    private T response;
}
