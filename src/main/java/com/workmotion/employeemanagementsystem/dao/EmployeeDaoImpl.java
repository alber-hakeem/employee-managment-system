package com.workmotion.employeemanagementsystem.dao;

import com.workmotion.employeemanagementsystem.dao.repo.EmployeeRepo;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDaoImpl implements EmployeeDao {
    private final EmployeeRepo employeeRepo;

    public EmployeeDaoImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public EmployeeRepo getRepository() {
        return employeeRepo;
    }

    @Override
    public void updateEmployeeEvents(Long employeeId, String events) {
        getRepository().updateEmployeeEvents(employeeId, events);
    }

    @Override
    public void updateEmployeeStatus(Long employeeId, String status) {
        getRepository().updateEmployeeStatus(employeeId, status);
    }
}
