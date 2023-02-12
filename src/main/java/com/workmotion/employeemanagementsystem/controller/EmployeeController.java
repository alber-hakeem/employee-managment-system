package com.workmotion.employeemanagementsystem.controller;

import com.workmotion.employeemanagementsystem.base.api.response.ApiResponse;
import com.workmotion.employeemanagementsystem.base.api.response.ApiResponseBuilder;
import com.workmotion.employeemanagementsystem.base.controller.BaseController;
import com.workmotion.employeemanagementsystem.dto.EmployeeDto;
import com.workmotion.employeemanagementsystem.enums.EmployeeEvents;
import com.workmotion.employeemanagementsystem.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController implements BaseController<EmployeeService, EmployeeDto> {
    private final ApiResponseBuilder<EmployeeDto> apiResponseBuilder;
    private final EmployeeService employeeService;

    @Override
    public EmployeeService getService() {
        return employeeService;
    }

    @Override
    public ApiResponseBuilder<EmployeeDto> getApiResponseBuilder() {
        return apiResponseBuilder;
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeDto> findById(@PathVariable Long id) {
        return getApiResponseBuilder().buildApiSuccessResponse(employeeService.findById(id));
    }

    @PostMapping
    public ApiResponse<EmployeeDto> create(@RequestBody EmployeeDto employeeDto) {
        return getApiResponseBuilder().buildApiSuccessResponse(employeeService.create(employeeDto));
    }

    @PutMapping("{id}/event/{event}")
    public ApiResponse<EmployeeDto> changeState(@PathVariable Long id, @PathVariable EmployeeEvents event) {
        return getApiResponseBuilder().buildApiSuccessResponse(employeeService.applyEmployeeEvent(id, event));
    }
}
