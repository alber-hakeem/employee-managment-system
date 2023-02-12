package com.workmotion.employeemanagementsystem.base.service;

import com.workmotion.employeemanagementsystem.base.dao.MessageDao;
import com.workmotion.employeemanagementsystem.base.dto.MessageDto;
import com.workmotion.employeemanagementsystem.base.localization.ExposedResourceMessageBundleSource;
import com.workmotion.employeemanagementsystem.base.localization.LocaleUtil;
import com.workmotion.employeemanagementsystem.base.model.LanguageEnum;
import com.workmotion.employeemanagementsystem.base.model.Message;
import com.workmotion.employeemanagementsystem.base.model.MessageTypeEnum;
import com.workmotion.employeemanagementsystem.base.transformer.MessageTransformer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageTransformer messageTransformer;
    private final MessageDao messageDao;
    private MessageSource messageSource;

    public MessageServiceImpl(MessageTransformer messageTransformer, MessageDao messageDao) {
        this.messageTransformer = messageTransformer;
        this.messageDao = messageDao;
    }

    @Override
    public MessageTransformer getTransformer() {
        return messageTransformer;
    }

    @Override
    public MessageDao getDao() {
        return messageDao;
    }

    @Override
    public MessageService getMessageService() {
        return this;
    }

    @Override
    public Message findByCodeAndLang(String code, LanguageEnum lang) {
        lang = lang == null ? LanguageEnum.EN : lang;
        return messageDao.findByCodeAndLang(code, lang).orElseGet(() ->
                messageDao.findByCodeAndLang(code, LanguageEnum.EN).get()
        );
    }

    @Override
    public String findMessageByCodeAndLang(String code, LanguageEnum lang) {
        Message message = findByCodeAndLang(code, lang);
        if (message == null || message.getMessage() == null)
            return "";
        return message.getMessage();
    }

    public String findMessageByCodeAndLang(String code) {
        return findMessageByCodeAndLang(code, LocaleUtil.getLang());
    }

    @Override
    public Message findByModuleAndCodeAndLang(String module, String code, LanguageEnum lang) {
        return messageDao.findByModuleAndCodeAndLang(module, code, lang).
                orElseGet(() -> messageDao.findByModuleAndCodeAndLang(module, code, LanguageEnum.EN).get());
    }

    @Override
    public String findMessageByModuleAndCodeAndLang(String module, String code, LanguageEnum lang) {
        return null;
    }

    @Override
    public String findMessageByModuleAndCodeAndLang(String module, String code) {
        return null;
    }

    @Override
    public List<Message> findByModuleAndTypeAndLang(String module, MessageTypeEnum type, LanguageEnum lang) {
        return messageDao.findByModuleAndTypeAndLang(module, type, lang);
    }

    @Override
    public List<Message> findByModuleAndType(String module, MessageTypeEnum type) {
        return messageDao.findByModuleAndType(module, type);
    }

    @Override
    public Map<String, Map<String, MessageDto>> findByModuleAndTypeGroupBy(String module, MessageTypeEnum type) {
        module = module.toUpperCase();
        List<Message> messages = messageDao.findByModuleAndType(module, type);
        List<MessageDto> messagesDTOs = messageTransformer.transformEntityToDTO(messages);
        Map<String, Map<String, MessageDto>> messagesMap = new HashMap<>();
        messagesDTOs.forEach(messageDto -> {
            messagesMap.putIfAbsent(messageDto.getCode(), new HashMap<>());
            messagesMap.get(messageDto.getCode()).putIfAbsent(messageDto.getLang().getValue(), messageDto);
        });

        return messagesMap;
    }

    @Override
    public List<Message> findResourceBundleMessages(LanguageEnum lang) {
        return getResourceBundleMessages(lang);
    }

    private List<Message> getResourceBundleMessages(LanguageEnum lang) {
        Properties properties = ((AnnotationConfigServletWebServerApplicationContext) messageSource).getBean("messageSource",
                ExposedResourceMessageBundleSource.class).getMessages(LocaleUtil.getLocale(lang));
        List<Message> messages = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                Message message = new Message();
                message.setCode(entry.getKey().toString());
                message.setLang(lang);
                message.setMessage(entry.getValue().toString());
                messages.add(message);
            }
        }
        return messages;
    }

    protected MessageSource getMessageSource() {
        return messageSource;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
