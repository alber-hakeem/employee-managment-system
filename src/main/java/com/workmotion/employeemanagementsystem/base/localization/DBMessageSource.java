package com.workmotion.employeemanagementsystem.base.localization;

import com.workmotion.employeemanagementsystem.base.service.MessageService;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DBMessageSource implements MessageSource {
    private final MessageService messageService;

    public DBMessageSource(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String getMessage(String s, Object[] objects, String s1, Locale locale) {
        return messageService.findMessageByCodeAndLang(s, LocaleUtil.getLang(locale));
    }

    @Override
    public String getMessage(String s, Object[] objects, Locale locale) throws NoSuchMessageException {
        return messageService.findMessageByCodeAndLang(s, LocaleUtil.getLang(locale));
    }

    @Override
    public String getMessage(MessageSourceResolvable messageSourceResolvable, Locale locale) throws NoSuchMessageException {
        return null;
    }
}
