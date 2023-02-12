package com.workmotion.employeemanagementsystem.service;

import com.workmotion.employeemanagementsystem.base.service.BaseService;
import com.workmotion.employeemanagementsystem.dao.EmployeeDao;
import com.workmotion.employeemanagementsystem.dto.EmployeeDto;
import com.workmotion.employeemanagementsystem.enums.EmployeeEvents;
import com.workmotion.employeemanagementsystem.model.Employee;
import com.workmotion.employeemanagementsystem.transformer.EmployeeTransformer;

public interface EmployeeService extends BaseService<Employee, EmployeeDto, EmployeeTransformer, EmployeeDao> {
    EmployeeDto applyEmployeeEvent(Long employeeId, EmployeeEvents event);
}
