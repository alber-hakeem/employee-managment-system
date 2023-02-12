package com.workmotion.employeemanagementsystem.service;

import com.workmotion.employeemanagementsystem.base.service.MessageService;
import com.workmotion.employeemanagementsystem.dao.EmployeeDao;
import com.workmotion.employeemanagementsystem.dto.EmployeeDto;
import com.workmotion.employeemanagementsystem.enums.EmployeeEvents;
import com.workmotion.employeemanagementsystem.enums.EmployeeStates;
import com.workmotion.employeemanagementsystem.manager.EmployeeEventManager;
import com.workmotion.employeemanagementsystem.model.Employee;
import com.workmotion.employeemanagementsystem.transformer.EmployeeTransformer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.AttributeConverter;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeTransformer employeeTransformer;
    private final EmployeeDao employeeDao;
    private final MessageService messageService;
    private final EmployeeEventManager employeeEventManager;
    private final AttributeConverter attributeConverter;

    @Override
    public EmployeeTransformer getTransformer() {
        return employeeTransformer;
    }

    @Override
    public EmployeeDao getDao() {
        return employeeDao;
    }

    @Override
    public MessageService getMessageService() {
        return messageService;
    }

    @Override
    public Employee doBeforeCreateEntity(Employee entity, EmployeeDto dto) {
        log.info("EmployeeService: doBeforeCreateEntity(entity, dto) - called");
        entity.setStatus(List.of(EmployeeStates.ADDED.toString()));
        return entity;
    }

    @Override
    @Transactional
    public EmployeeDto applyEmployeeEvent(Long employeeId, EmployeeEvents event) {
        log.info("EmployeeService: applyEmployeeEvent(" + employeeId + ", " + event + ") - called");
        EmployeeDto employeeDto = findById(employeeId);
        employeeDto.getEvents().add(event.toString());
        employeeEventManager.apply(employeeDto.getId(), employeeDto.getEvents());
        getDao().updateEmployeeEvents(employeeId, (String) attributeConverter.convertToDatabaseColumn(employeeDto.getEvents()));
//
        return findById(employeeId);
    }


}
