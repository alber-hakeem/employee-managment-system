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
@Table(name = "core_config" ,uniqueConstraints = @UniqueConstraint(columnNames ={"config_key"}))
public class CoreConfig extends BaseEntity {

    @Id
    @SequenceGenerator(name="core_config_id_seq",sequenceName="core_config_id_seq", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="core_config_id_seq")
    private Long id;

    @Column(name = "config_key")
    private String configKey;

    @Column(name = "config_value")
    private String configValue;

    @Column(name = "config_type")
    private String configType;


}
