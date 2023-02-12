package com.workmotion.employeemanagementsystem.manager;

import java.util.List;
import java.util.Set;

public interface EmployeeEventManager {
    Boolean apply(Long employeeId, List<String> events);
}