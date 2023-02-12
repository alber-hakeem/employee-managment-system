package com.workmotion.employeemanagementsystem.base.transformer;


import com.workmotion.employeemanagementsystem.base.dto.BaseDto;
import com.workmotion.employeemanagementsystem.base.model.BaseEntity;
import com.workmotion.employeemanagementsystem.base.transformer.mapper.BaseMapper;

import java.util.List;
import java.util.stream.Collectors;


public interface BaseTransformer<Entity extends BaseEntity, Dto extends BaseDto, Mapper extends BaseMapper> {

    public Mapper getMapper();

    default Dto transformEntityToDTO(Entity entity) {
        return (Dto) getMapper().entityToDto(entity);
    };

    default List<Dto> transformEntityToDTO(List<Entity> entities) {
        return entities.stream().map(entity -> transformEntityToDTO(entity)).collect(Collectors.toList());
    }

    default Entity transformDTOToEntity(Dto dto) {
        return (Entity) getMapper().dtoToEntity(dto);
    }

    default List<Entity> transformDTOToEntity(List<Dto> dtos) {
        return dtos.stream().map(dto -> transformDTOToEntity(dto)).collect(Collectors.toList());
    }

    default void updateEntity(Dto dto, Entity entity) {
        getMapper().updateEntity(dto, entity);
        doAfterUpdate(dto,entity);
    }

    default void doAfterUpdate(Dto dto, Entity entity){
        //implementation left to classes
    }
}
