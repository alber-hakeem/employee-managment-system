package com.workmotion.employeemanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workmotion.employeemanagementsystem.base.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeDto extends BaseDto {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Positive
    private Integer age;
    private List<String> status = new ArrayList<>();
    @JsonIgnore
    private List<String> events = new ArrayList<>();
}
