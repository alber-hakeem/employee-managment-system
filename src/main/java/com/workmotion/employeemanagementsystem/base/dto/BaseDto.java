package com.workmotion.employeemanagementsystem.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Base class for Dtos
 * contains audit fields
 * @author Alber Rashad
 * @created 29/10/2022
 * @description
  */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseDto {
    private LocalDateTime creationDate;
    private LocalDateTime lastModificationDate;
    private Boolean markedAsDeleted=false;
}
