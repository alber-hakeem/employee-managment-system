package com.workmotion.employeemanagementsystem.base.service;

import com.workmotion.employeemanagementsystem.base.dao.MessageDao;
import com.workmotion.employeemanagementsystem.base.dto.MessageDto;
import com.workmotion.employeemanagementsystem.base.model.LanguageEnum;
import com.workmotion.employeemanagementsystem.base.model.Message;
import com.workmotion.employeemanagementsystem.base.model.MessageTypeEnum;
import com.workmotion.employeemanagementsystem.base.transformer.MessageTransformer;
import org.springframework.context.MessageSourceAware;

import java.util.List;
import java.util.Map;


public interface MessageService extends BaseService<Message, MessageDto, MessageTransformer,MessageDao>, MessageSourceAware {
    Message findByCodeAndLang(String code, LanguageEnum lang);
    String findMessageByCodeAndLang(String code, LanguageEnum lang);
    String findMessageByCodeAndLang(String code);
    Message findByModuleAndCodeAndLang(String module,String code, LanguageEnum lang);
    String findMessageByModuleAndCodeAndLang(String module,String code, LanguageEnum lang);
    String findMessageByModuleAndCodeAndLang(String module,String code);
    List<Message> findByModuleAndTypeAndLang(String module, MessageTypeEnum type, LanguageEnum lang);
    List<Message> findByModuleAndType(String module, MessageTypeEnum type);
    Map<String, Map<String, MessageDto>>findByModuleAndTypeGroupBy(String module, MessageTypeEnum type);
    List<Message> findResourceBundleMessages(LanguageEnum lang);
}
