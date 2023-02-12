package com.workmotion.employeemanagementsystem.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "locale_message" ,uniqueConstraints = @UniqueConstraint(columnNames ={"module","code","lang"}))
public class Message extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module", nullable = false)
    private String module;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "lang", nullable = false)
    @Enumerated(EnumType.STRING)
    private LanguageEnum lang;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageTypeEnum type;

    @Column(name = "icon_url")
    private String iconUrl;
}
