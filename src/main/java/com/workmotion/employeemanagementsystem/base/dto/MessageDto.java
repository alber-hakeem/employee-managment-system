package com.workmotion.employeemanagementsystem.base.dto;

import com.workmotion.employeemanagementsystem.base.model.LanguageEnum;
import com.workmotion.employeemanagementsystem.base.model.MessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageDto extends BaseDto {
    private Long id;
    private String module;
    private String code;
    private String message;
    private LanguageEnum lang;
    private MessageTypeEnum type;
    private String iconUrl;
}
