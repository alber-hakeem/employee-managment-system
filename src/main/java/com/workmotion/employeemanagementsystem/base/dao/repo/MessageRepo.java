package com.workmotion.employeemanagementsystem.base.dao.repo;

import com.workmotion.employeemanagementsystem.base.model.LanguageEnum;
import com.workmotion.employeemanagementsystem.base.model.Message;
import com.workmotion.employeemanagementsystem.base.model.MessageTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    Optional<Message> findByCodeAndLang(String code, LanguageEnum lang);

    Optional<Message> findByModuleAndCodeAndLang(String module, String code, LanguageEnum lang);

    List<Message> findByModuleAndTypeAndLang(String module, MessageTypeEnum type, LanguageEnum lang);

    @Query("SELECT m FROM Message m WHERE m.module = :module and m.type = :type")
    List<Message> findByModuleAndType(String module, MessageTypeEnum type);
}
