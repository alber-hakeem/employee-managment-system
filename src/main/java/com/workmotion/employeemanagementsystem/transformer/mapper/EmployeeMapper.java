package com.workmotion.employeemanagementsystem.transformer.mapper;

import com.workmotion.employeemanagementsystem.base.transformer.mapper.BaseMapper;
import com.workmotion.employeemanagementsystem.base.transformer.mapper.MapStructCentralConfig;
import com.workmotion.employeemanagementsystem.dto.EmployeeDto;
import com.workmotion.employeemanagementsystem.model.Employee;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, config = MapStructCentralConfig.class)
public interface EmployeeMapper extends BaseMapper<Employee, EmployeeDto> {
}
