package com.workmotion.employeemanagementsystem.base.transformer;

import com.workmotion.employeemanagementsystem.base.dto.MessageDto;
import com.workmotion.employeemanagementsystem.base.model.Message;
import com.workmotion.employeemanagementsystem.base.transformer.mapper.MessageMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageTransformer implements BaseTransformer<Message, MessageDto, MessageMapper> {
    private final MessageMapper messageMapper;

    public MessageTransformer(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Override
    public MessageMapper getMapper() {
        return messageMapper;
    }
}
