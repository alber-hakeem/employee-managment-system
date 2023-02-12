package com.workmotion.employeemanagementsystem.base.api.response;

import com.workmotion.employeemanagementsystem.base.dto.BaseDto;
import com.workmotion.employeemanagementsystem.base.localization.CoreMessageConstants;
import com.workmotion.employeemanagementsystem.base.service.MessageService;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.*;


@Component
public class ApiResponseBuilderImpl<Dto extends BaseDto> implements ApiResponseBuilder<Dto> {

    private final MessageService messageService;

    public ApiResponseBuilderImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public ApiResponse<Dto> buildApiResponse(Dto dto, String message) {
        return ApiResponse.<Dto>builder().message(message).response(dto).build();
    }

    @Override
    public ApiResponse<?> buildApiResponse(Object obj, String message) {
        return ApiResponse.builder().message(message).response(obj).build();
    }

    @Override
    public ApiResponse<List<Dto>> buildApiResponse(List<Dto> dtoList, String message) {
        return ApiResponse.<List<Dto>>builder().message(message).response(dtoList).build();
    }

    @Override
    public ApiResponse<PaginationResponse<Dto>> buildApiResponse(PaginationResponse<Dto> paginationResponse, String message) {
        return ApiResponse.<PaginationResponse<Dto>>builder().message(message).response(paginationResponse).build();
    }

    @Override
    public ApiResponse<Dto> buildApiSuccessResponse() {
        return buildApiSuccessResponse(messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_SUCCESS));
    }


    @Override
    public ApiResponse<Dto> buildApiSuccessResponse(String message) {
        return ApiResponse.<Dto>builder().message(message).build();
    }

    @Override
    public ApiResponse<Dto> buildApiSuccessResponse(Dto dto) {
        return buildApiResponse(dto, messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_SUCCESS));
    }

    @Override
    public ApiResponse<?> buildApiSuccessResponse(Object obj) {
        return buildApiResponse(obj, messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_SUCCESS));
    }

    @Override
    public ApiResponse<List<Dto>> buildApiSuccessResponse(List<Dto> dtoList) {
        return buildApiResponse(dtoList, messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_SUCCESS));
    }

    @Override
    public ApiResponse<PaginationResponse<Dto>> buildApiSuccessResponse(PaginationResponse<Dto> paginationResponse) {
        return buildApiResponse(paginationResponse, messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_SUCCESS));
    }

    @Override
    public ApiResponse<Dto> buildApiFailureResponse() {
        return buildApiFailureResponse(messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_FAILED));
    }

    @Override
    public ApiResponse<Dto> buildApiFailureResponse(String message) {
        return ApiResponse.<Dto>builder().message(message).build();
    }

    @Override
    public ApiResponse<Dto> buildApiFailureResponse(String message,String appCode, String detailedMessages) {
        return ApiResponse.<Dto>builder().message(message).appCode(appCode).detailedMessage(detailedMessages).build();
    }

    @Override
    public ApiResponse<Dto> buildApiFailureResponse(String message, String detailedMessages) {
        return ApiResponse.<Dto>builder().message(message).detailedMessage(detailedMessages).build();
    }

    @Override
    public ApiResponse<Dto> buildApiFailureResponse(Dto dto) {
        return buildApiResponse(dto, messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_FAILED));
    }

    @Override
    public ApiResponse<List<Dto>> buildApiFailureResponse(List<Dto> dtoList) {
        return buildApiResponse(dtoList, messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_FAILED));
    }

    @Override
    public ApiResponse<PaginationResponse<Dto>> buildApiFailureResponse(PaginationResponse<Dto> paginationResponse) {
        return buildApiResponse(paginationResponse, messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_FAILED));
    }

    @Override
    public ApiResponse<Map<String, Map<String, Map<String, Dto>>>> buildApiResponse(Map<String, Map<String, Map<String, Dto>>> DTOsMap, String message) {
        return ApiResponse.<Map<String, Map<String, Map<String, Dto>>>>builder().message(message).response(DTOsMap).build();
    }

    @Override
    public ApiResponse<Map<String, Map<String, Map<String, Dto>>>> buildApiSuccessResponse(Map<String, Map<String, Map<String, Dto>>> DTOsMap) {
        return buildApiResponse(DTOsMap, messageService.findMessageByCodeAndLang(CoreMessageConstants.REQUEST_SUCCESS));
    }

    @Override
    public ApiResponse<Dto> buildApiFailureResponse(List<FieldError> fieldErrors, List<ObjectError> objectErrors) {
        Map<String, Set<String>> errors = addValidationErrors(fieldErrors, objectErrors);
        return ApiResponse.<Dto>builder().message(messageService.findMessageByCodeAndLang(CoreMessageConstants.VALIDATION_ERROR
                )).errors(errors).build();
    }

    @Override
    public ApiResponse<Dto> buildApiFailureResponse(Set<ConstraintViolation<?>> constraintViolations) {
        Map<String, Set<String>> errors = addValidationErrors(constraintViolations);
        return ApiResponse.<Dto>builder().message(messageService.findMessageByCodeAndLang(CoreMessageConstants.VALIDATION_ERROR
                )).errors(errors).build();
    }

    private Map<String, Set<String>> addValidationErrors(List<FieldError> fieldErrors, List<ObjectError> objectErrors) {
        Map<String, Set<String>> errors = new HashMap<>();
        fieldErrors.forEach(fieldError -> addValidationError(fieldError.getField(), fieldError.getDefaultMessage(), errors));
        objectErrors.forEach(objectError -> addValidationError(objectError.getObjectName(), objectError.getDefaultMessage(), errors));
        return errors;
    }

    private Map<String, Set<String>> addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        Map<String, Set<String>> errors = new HashMap<>();
        constraintViolations.forEach(cv -> addValidationError(((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getMessage(), errors));
        return errors;
    }

    private void addValidationError(String name, String message, Map<String, Set<String>> errors) {
        if (errors.get(name) != null) {
            errors.get(name).add(message);
        } else {
            Set<String> subErrors = new HashSet<>();
            errors.put(name, subErrors);
            subErrors.add(message);
        }
    }
}
