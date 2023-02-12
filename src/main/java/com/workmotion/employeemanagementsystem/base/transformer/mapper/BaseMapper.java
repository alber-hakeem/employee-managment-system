package com.workmotion.employeemanagementsystem.base.transformer.mapper;

import com.workmotion.employeemanagementsystem.base.dto.BaseDto;
import com.workmotion.employeemanagementsystem.base.model.BaseEntity;
import org.mapstruct.MappingTarget;


public interface BaseMapper<Entity extends BaseEntity, Dto extends BaseDto> {
    Entity dtoToEntity(Dto dto);
    Dto entityToDto(Entity entity);
    void updateEntity(Dto dto, @MappingTarget Entity entity);
}
