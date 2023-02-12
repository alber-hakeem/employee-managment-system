package com.workmotion.employeemanagementsystem.base.dao;

import com.workmotion.employeemanagementsystem.base.api.request.PaginationRequest;
import com.workmotion.employeemanagementsystem.base.api.request.SortingBy;
import com.workmotion.employeemanagementsystem.base.api.request.SortingDirection;
import com.workmotion.employeemanagementsystem.base.exception.custom.BusinessException;
import com.workmotion.employeemanagementsystem.base.localization.CoreMessageConstants;
import com.workmotion.employeemanagementsystem.base.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Base class for entities daos
 * should be implemented by all the Dao
 * contains common methods for the working with DB
 * uses a repository to do the actual work

 */
public interface BaseDao<Entity extends BaseEntity,Repository extends JpaRepository<Entity,Long>> {
    /**
     * entity repo
     * @return
     */
    Repository getRepository();

    default Entity doBeforeCreate(Entity entity){
        return entity;
    }
    default Entity doBeforeCreateOrUpdate(Entity entity){
        return entity;
    }
    /**
     * create new row in DB
     * @param entity
     * @return
     */
    default Entity create(Entity entity){
        entity = doBeforeCreate(entity);
        return getRepository().save(entity);
    }

    default Entity createOrUpdate(Entity entity){
        entity = doBeforeCreateOrUpdate(entity);
        return getRepository().save(entity);
    }

    default List<Entity> createOrUpdate(List<Entity> entities){
        entities.stream().forEach(entity -> doBeforeCreateOrUpdate(entity));
        return getRepository().saveAll(entities);
    }

    /**
     * create new rows in DB
     * @param entities
     * @return
     */
    default List<Entity> create(List<Entity> entities){
        return getRepository().saveAll(entities);
    }
    /**
     * update an existing row in DB
     * @param entity
     * @return
     */
    default Entity update(Entity entity){
     //   entity = doBeforeUpdate(entity);
        return getRepository().save(entity);
    }

    default List<Entity> update(List<Entity> entities){
        return getRepository().saveAll(entities);
    }


    /**
     * find entity by Id
     * @param id
     * @return
     */
    default Optional<Entity> findById(Long id){
        return getRepository().findById(id);
    }

    /**
     * get all entities
     * @return
     */
    default List<Entity> findAll(){
        return getRepository().findAll();
    }
    default List<Entity> findAll(Boolean markedAsDeleted){return new LinkedList<>();}

    /**
     * get all entities
     * @return
     */
    default List<Entity> findAllById(Iterable<Long> iterable){
        return getRepository().findAllById(iterable);
    }

    /**
     * find entities by pagination and sorting
     * @param paginationRequest
     * @return
     */
    default Page<Entity> findAll(PaginationRequest paginationRequest){
        //subtract 1 from page number because library start from page 0
        PageRequest pageRequest = PageRequest.
                of(paginationRequest.getPageNumber()-1, paginationRequest.getPageSize(), buildSort(paginationRequest));
        return getRepository().findAll(pageRequest);
    }

    default Sort buildSort(PaginationRequest paginationRequest){
        //TODO should handle if sorting list is null or empty, to use Id as the default sorting field
       return Sort.by(
        paginationRequest.getSortingByList().stream().map(sortingBy ->
           sortingBy.getIsNumber() ? new Sort.Order(sortingBy.getDirection().equals(SortingDirection.ASC)?Sort.Direction.ASC:Sort.Direction.DESC,sortingBy.getFieldName())
                   :new Sort.Order(sortingBy.getDirection().equals(SortingDirection.ASC)?Sort.Direction.ASC:Sort.Direction.DESC,sortingBy.getFieldName()).ignoreCase()
        ).collect(Collectors.toList()));
    }
    default <T extends Class<Entity>> Page<Entity> findAll(PaginationRequest paginationRequest, T entityCls) {
        PageRequest pageRequest = PageRequest.
                of(paginationRequest.getPageNumber() - 1, paginationRequest.getPageSize(), buildSort(paginationRequest, entityCls));
        return getRepository().findAll(pageRequest);
    }


    default <T extends Class<Entity>> Sort buildSort(PaginationRequest paginationRequest, T entityCls) {
        List<SortingBy> sortingByList = paginationRequest.getSortingByList();
        if (sortingByList == null) {
            sortingByList = new ArrayList<>();
            sortingByList.add(new SortingBy("id", SortingDirection.ASC));
        }
        return Sort.by(
                sortingByList.stream().map(sortingBy -> {
                    Field orderField = ReflectionUtils.findField(entityCls, sortingBy.getFieldName());
                    if (orderField == null) throw new BusinessException(CoreMessageConstants.MALFORMED_JSON_REQUEST);
                    if (orderField.getType().equals(String.class))
                        return new Sort.Order(sortingBy.getDirection().equals(SortingDirection.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC, sortingBy.getFieldName()).ignoreCase();
                    return new Sort.Order(sortingBy.getDirection().equals(SortingDirection.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC, sortingBy.getFieldName());
                }).collect(Collectors.toList()));
    }
    /**
     * get last id in db
     */
    default Long getLastId() {
        return 0l;
    }
}
