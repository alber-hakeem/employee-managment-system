package com.workmotion.employeemanagementsystem.transformer;

import com.workmotion.employeemanagementsystem.base.transformer.BaseTransformer;
import com.workmotion.employeemanagementsystem.dto.EmployeeDto;
import com.workmotion.employeemanagementsystem.model.Employee;
import com.workmotion.employeemanagementsystem.transformer.mapper.EmployeeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmployeeTransformer implements BaseTransformer<Employee, EmployeeDto, EmployeeMapper> {
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeMapper getMapper() {
        return employeeMapper;
    }
}
