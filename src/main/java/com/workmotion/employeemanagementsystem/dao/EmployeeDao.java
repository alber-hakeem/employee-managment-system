package com.workmotion.employeemanagementsystem.dao;

import com.workmotion.employeemanagementsystem.base.dao.BaseDao;
import com.workmotion.employeemanagementsystem.dao.repo.EmployeeRepo;
import com.workmotion.employeemanagementsystem.model.Employee;

public interface EmployeeDao extends BaseDao<Employee, EmployeeRepo> {
    void updateEmployeeEvents(Long employeeId, String events);
    void updateEmployeeStatus(Long employeeId, String status);
}
