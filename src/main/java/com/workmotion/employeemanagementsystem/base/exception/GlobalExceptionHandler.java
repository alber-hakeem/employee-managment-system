package com.workmotion.employeemanagementsystem.base.exception;

import com.workmotion.employeemanagementsystem.base.api.response.ApiResponse;
import com.workmotion.employeemanagementsystem.base.api.response.ApiResponseBuilder;
import com.workmotion.employeemanagementsystem.base.exception.custom.BusinessException;
import com.workmotion.employeemanagementsystem.base.exception.custom.ResourceNotFoundException;
import com.workmotion.employeemanagementsystem.base.service.MessageService;
import com.workmotion.employeemanagementsystem.base.util.CoreMessageConstants;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApiResponseBuilder apiResponseBuilder;
    private final MessageService messageService;

    public GlobalExceptionHandler(ApiResponseBuilder apiResponseBuilder, MessageService messageService) {
        this.apiResponseBuilder = apiResponseBuilder;
        this.messageService = messageService;
    }

    /********************* Custom Exceptions *********************/

    /**
     * Handles BusinessException. Created when violating business rules
     *
     * @param ex the BusinessException
     * @return the ApiResponse object
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity handleBusinessError(BusinessException ex) {
        logger.error("Exception Occurred", ex);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(ex.getMessage(), ex.getAppCode(), ex.getDetailedMessage()), BAD_REQUEST);
    }

    /**
     * Handles ResourceNotFoundException. Thrown when trying to retrieve resource
     * That's not exist in the database
     *
     * @param ex the ResourceNotFoundException
     * @return the ApiResponse object
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity handleResourceNotFound(ResourceNotFoundException ex) {
        logger.error("Exception Occurred", ex);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(ex.getMessage()), NOT_FOUND);
    }


    /********************* Common Exceptions *********************/

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiResponse object
     */
    @Override
    protected ResponseEntity handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Exception Occurred", ex);
        String error = ex.getParameterName() + getLocaleMessage(CoreMessageConstants.MISSING_PARAMETER);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(error,
                ex.getLocalizedMessage()), BAD_REQUEST);
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiResponse object
     */
    @Override
    protected ResponseEntity handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        logger.error("Exception Occurred", ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(getLocaleMessage(CoreMessageConstants.MEDIA_TYPE_NOT_SUPPORTED));
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(
                builder.substring(0, builder.length() - 2), ex.getLocalizedMessage()), UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiResponse object
     */
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                          HttpStatus status,
                                                          WebRequest request) {
        logger.error("Exception Occurred", ex);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(ex.getBindingResult().getFieldErrors(),
                ex.getBindingResult().getGlobalErrors()), BAD_REQUEST);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiResponse object
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
        logger.error("Exception Occurred", ex);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(ex.getConstraintViolations()), BAD_REQUEST);
    }

    /**
     * Handles EntityNotFoundException.
     * <p>
     * Thrown by the persistence provider when an entity reference obtained by EntityManager.getReference is accessed
     * but the entity does not exist.
     * Thrown when EntityManager.refresh is called and the object no longer exists in the database.
     * Thrown when EntityManager.lock is used with pessimistic locking is used and the entity no longer exists
     * in the database.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiResponse object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity handleEntityNotFound(EntityNotFoundException ex) {
        logger.error("Exception Occurred", ex);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(ex.getLocalizedMessage()), OK);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiResponse object
     */
    @Override
    protected ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                          HttpHeaders headers, HttpStatus status,
                                                          WebRequest request) {
        logger.error("Exception Occurred", ex);
        String error = getLocaleMessage(CoreMessageConstants.MALFORMED_JSON_REQUEST);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(
                error, ex.getLocalizedMessage()), BAD_REQUEST);
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiResponse object
     */
    @Override
    protected ResponseEntity handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                          HttpHeaders headers, HttpStatus status,
                                                          WebRequest request) {
        logger.error("Exception Occurred", ex);
        String error = getLocaleMessage(CoreMessageConstants.ERROR_WRITING_JSON);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(
                error, ex.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle NoHandlerFoundException.
     * <p>
     * By default when the DispatcherServlet can't find a handler for a request it sends a 404 response. However if its
     * property "throwExceptionIfNoHandlerFound" is set to true this exception is raised and may be handled with a
     * configured HandlerExceptionResolver.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return the ApiResponse object
     */
    @Override
    protected ResponseEntity handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                           HttpStatus status, WebRequest request) {
        logger.error("Exception Occurred", ex);
        String message = getLocaleMessage(CoreMessageConstants.METHOD_NOT_FOUND);
        message = String.format(message, ex.getHttpMethod(), ex.getRequestURL());
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(message,
                ex.getLocalizedMessage()), NOT_FOUND);
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiResponse object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        logger.error("Exception Occurred", ex);

        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintException = (ConstraintViolationException) ex.getCause();

            try {
                String constrainMessage = getLocaleMessage(constraintException.getConstraintName());
                if (!constrainMessage.equals(""))
                    return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(
                            constrainMessage, ex.getLocalizedMessage()), CONFLICT);

            } catch (NoSuchElementException exception) {}

            return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(
                    constraintException.getConstraintName(), ex.getLocalizedMessage()), CONFLICT);
        }

        String message = getLocaleMessage(CoreMessageConstants.DATABASE_ERROR);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(
                message, ex.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle MethodArgumentTypeMismatchException, handle parameter mismatch
     *
     * @param ex the MethodArgumentTypeMismatchException
     * @return the ApiResponse object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.error("Exception Occurred", ex);
        String message = getLocaleMessage(CoreMessageConstants.PARAMETER_MISMATCH);
        message = String.format(message, ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(message,
                ex.getLocalizedMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity handleConstrainException(TransactionSystemException ex) {
        logger.error("Exception Occurred", ex);
        if (ex.getRootCause() instanceof javax.validation.ConstraintViolationException) {
            javax.validation.ConstraintViolationException constraintViolationException = (javax.validation.ConstraintViolationException) ex.getRootCause();
            return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(constraintViolationException.getConstraintViolations()), BAD_REQUEST);
        }
        String message = getLocaleMessage(CoreMessageConstants.TRANSACTION_SYSTEM_ERROR);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(message,
                ex.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiResponse object
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex) {
        logger.error("Exception Occurred", ex);
        return buildResponseEntity(this.apiResponseBuilder.buildApiFailureResponse(
                getLocaleMessage(CoreMessageConstants.UNEXPECTED_ERROR),
                ex.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiResponse> buildResponseEntity(ApiResponse apiResponse, HttpStatus code) {
        return new ResponseEntity<ApiResponse>(apiResponse, code);
    }
    private String getLocaleMessage(String code) {
        return messageService.findMessageByCodeAndLang(code);
    }

}
