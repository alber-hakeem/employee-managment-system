package com.workmotion.employeemanagementsystem.base.controller;

import com.workmotion.employeemanagementsystem.base.api.response.ApiResponseBuilder;
import com.workmotion.employeemanagementsystem.base.dto.BaseDto;
import com.workmotion.employeemanagementsystem.base.service.BaseService;

/**
 * Base Class for controllers
 * Each controller should be for a dto
 */
public interface BaseController<Service extends BaseService,Dto extends BaseDto> {
    /**
     * Service to be used by the controller
     * @return
     */
    Service getService();

    /**
     * Every Controller should use the response API builder to build the response
     * @return
     */
    ApiResponseBuilder getApiResponseBuilder();
}
