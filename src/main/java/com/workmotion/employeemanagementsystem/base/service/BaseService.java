package com.workmotion.employeemanagementsystem.base.service;

import com.workmotion.employeemanagementsystem.base.api.request.PaginationRequest;
import com.workmotion.employeemanagementsystem.base.api.response.PaginationResponse;
import com.workmotion.employeemanagementsystem.base.dao.BaseDao;
import com.workmotion.employeemanagementsystem.base.dto.BaseDto;
import com.workmotion.employeemanagementsystem.base.localization.CoreMessageConstants;
import com.workmotion.employeemanagementsystem.base.model.BaseEntity;
import com.workmotion.employeemanagementsystem.base.transformer.BaseTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface BaseService<Entity extends BaseEntity, Dto extends BaseDto, Transformer extends BaseTransformer, Dao extends BaseDao> {

    Transformer getTransformer();

    Dao getDao();

    /**
     * do before create entity in DB
     *
     * @param entity
     * @return
     */
    default Entity doBeforeCreateEntity(Entity entity, Dto dto) {
        return entity;
    }

    default List<Entity> doBeforeCreateEntity(List<Entity> entities, List<Dto> dtos) {
        return entities;
    }

    /**
     * do before update the entity record in DB
     *
     * @param dto
     * @return
     */
    default Entity doBeforeUpdateEntity(Entity entity, Dto dto) {
        return entity;
    }

    default Entity doCreate(Dto dto) {
        Entity dtoToEntity = (Entity) getTransformer().transformDTOToEntity(dto);
        Entity toBeSavedEntity = doBeforeCreateEntity(dtoToEntity, dto);
        Entity savedEntity = (Entity) getDao().create(toBeSavedEntity);
        return savedEntity;
    }

    default List<Entity> doCreate(List<Dto> dtos) {
        List<Entity> entities = (List<Entity>) getTransformer().transformDTOToEntity(dtos);
        List<Entity> toBeSavedEntities = doBeforeCreateEntity(entities, dtos);
        List<Entity> savedDEntities = (List<Entity>) getDao().create(toBeSavedEntities);
        return savedDEntities;
    }

    default Dto create(Dto dto) {
        Entity savedDEntity = doCreate(dto);
        return (Dto) getTransformer().transformEntityToDTO(savedDEntity);
    }

    default Entity doUpdate(Dto dto, Long id) {
        Optional<Entity> entity = getDao().findById(id);
        if (!entity.isPresent())
            throw new EntityNotFoundException(getEntityNotFoundExceptionMessage());
        Entity dtoToEntity = entity.get();
        getTransformer().updateEntity(dto, dtoToEntity);
        Entity toBeSavedEntity = doBeforeUpdateEntity(dtoToEntity, dto);
        Entity savedDEntity = (Entity) getDao().update(toBeSavedEntity);
        return savedDEntity;
    }

    default Dto update(Dto dto, Long id) {
        Entity savedDEntity = doUpdate(dto, id);
        return (Dto) getTransformer().transformEntityToDTO(savedDEntity);
    }

    default List<Dto> create(List<Dto> dtos) {
        List<Entity> savedDEntities = doCreate(dtos);
        return (List<Dto>) getTransformer().transformEntityToDTO(savedDEntities);
    }

    default Dto findById(Long id) {
        return (Dto) getTransformer().transformEntityToDTO(findEntityById(id).get());
    }

    default Optional<Entity> findEntityById(Long id) {
        Optional<Entity> optionalEntity = getDao().findById(id);
        if (!optionalEntity.isPresent())
            throw new EntityNotFoundException(getEntityNotFoundExceptionMessage());
        return optionalEntity;
    }

    default List<Dto> findAll() {
        return getTransformer().transformEntityToDTO(getDao().findAll());
    }

    default List<Dto> findAll(Boolean markedAsDeleted) {
        return getTransformer().transformEntityToDTO(getDao().findAll(markedAsDeleted));
    }

    default List<Dto> findAllById(Iterable<Long> iterable) {
        return getTransformer().transformEntityToDTO(getDao().findAllById(iterable));
    }

    default List<Dto> findAllEntitiesById(Iterable<Long> iterable) {
        return getDao().findAllById(iterable);
    }

    default PaginationResponse<Dto> findAll(PaginationRequest paginationRequest) {
        Page<Entity> entities = getDao().findAll(paginationRequest);
        return buildPaginationResponse(entities);
    }

    default <T extends Class<Entity>> PaginationResponse<Dto> findAll(PaginationRequest paginationRequest, T entityCls) {
        Page<Entity> entities = getDao().findAll(paginationRequest, entityCls);
        return buildPaginationResponse(entities);
    }

    default PaginationResponse<Dto> buildPaginationResponse(Page<Entity> entities) {
        return PaginationResponse.builder().pageNumber(entities.getNumber() + 1).pageSize(entities.getSize())
                .isFirst(entities.isFirst()).isLast(entities.isLast()).
                result(getTransformer().transformEntityToDTO(entities.getContent()))
                .totalNumberOfElements(entities.getTotalElements()).totalNumberOfPages(entities.getTotalPages()).build();
    }

    default PageRequest buildPageRequest(PaginationRequest paginationRequest) {
        List<Sort.Order> orders = paginationRequest.getSortingByList().stream().map(sortingBy -> {
            return new Sort.Order(Sort.Direction.valueOf(sortingBy.getDirection().getValue()), sortingBy.getFieldName());
        }).collect(Collectors.toList());
        return PageRequest.of(paginationRequest.getPageNumber() - 1, paginationRequest.getPageSize(), Sort.by(orders));
    }

    default Entity checkExist(Long id) {
        Optional<Entity> optionalEntity = findEntityById(id);
        if (!optionalEntity.isPresent())
            throw new EntityNotFoundException(getEntityNotFoundExceptionMessage());
        return optionalEntity.get();
    }

    default String getLocaleMessage(String code) {
        return getMessageService().findMessageByCodeAndLang(code);
    }

    MessageService getMessageService();

    /**
     * get current entity name
     * override to display it in error messages
     *
     * @return entity name
     */
    default String getEntityName() {
        return null;
    }

    private String getCurrentEntityName() {
        try {
            if (getEntityName() != null || getEntityName().equals(""))
                return getEntityName() + " ";

        } catch (NullPointerException exception) {}

        return getClass().getSimpleName().replace("ServiceImpl", " ");
    }

    private String getEntityNotFoundExceptionMessage() {
        return getCurrentEntityName() + getLocaleMessage(CoreMessageConstants.IS_NOT_FOUND);
    }

}
