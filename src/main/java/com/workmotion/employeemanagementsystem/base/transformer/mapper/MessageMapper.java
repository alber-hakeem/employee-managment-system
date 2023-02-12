package com.workmotion.employeemanagementsystem.base.transformer.mapper;

import com.workmotion.employeemanagementsystem.base.dto.MessageDto;
import com.workmotion.employeemanagementsystem.base.model.Message;
import com.workmotion.employeemanagementsystem.base.transformer.MessageTransformer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MessageTransformer.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        config = MapStructCentralConfig.class)
public interface MessageMapper extends BaseMapper<Message, MessageDto> {
}
