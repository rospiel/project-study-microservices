package com.study.microservices.studyapplication.exceptionhandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.study.microservices.studyapplication.core.validation.ValidationException;
import com.study.microservices.studyapplication.domain.exception.EntityAlreadyInUseException;
import com.study.microservices.studyapplication.domain.exception.UnprocessableEntityException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.study.microservices.studyapplication.exceptionhandler.ProblemType.ENTITY_ALREADY_IN_USE;
import static com.study.microservices.studyapplication.exceptionhandler.ProblemType.ENTITY_CANNOT_BE_PROCESSED;
import static com.study.microservices.studyapplication.exceptionhandler.ProblemType.UNREADABLE_BODY;
import static com.study.microservices.studyapplication.exceptionhandler.ProblemType.INVALIDATED_PROPERTY;
import static java.lang.Boolean.FALSE;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private final String ERROR_MESSAGE_BAD_REQUEST = "There is/are some invalidated properties, please check and submit again.";

    @ExceptionHandler(EntityAlreadyInUseException.class)
    public ResponseEntity<?> handleEntityAlreadyInUseException(EntityAlreadyInUseException error, WebRequest request) {
        HttpStatus status = CONFLICT;

        ErrorDto errorDto = createErrorDto(status.value(),
                ENTITY_ALREADY_IN_USE, error.getMessage()).build();

        return handleExceptionInternal(error,
                errorDto,
                new HttpHeaders(),
                status,
                request);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<?> handleUnprocessableEntityException(UnprocessableEntityException error, WebRequest request) {
        HttpStatus status = UNPROCESSABLE_ENTITY;

        ErrorDto errorDto = createErrorDto(status.value(),
                ENTITY_CANNOT_BE_PROCESSED, error.getMessage()).build();

        return handleExceptionInternal(error,
                errorDto,
                new HttpHeaders(),
                status,
                request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException error, WebRequest request) {
        HttpStatus status = INTERNAL_SERVER_ERROR;

        ErrorDto errorDto = createErrorDto(status.value(),
                UNREADABLE_BODY, error.getMessage()).build();

        return handleExceptionInternal(error,
                errorDto,
                new HttpHeaders(),
                status,
                request);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleIllegalArgumentException(ValidationException error, WebRequest request) {
        HttpStatus status = BAD_REQUEST;

        List<FieldErrorDto> fields = getFieldsErrors(error.getBindingResult());

        ErrorDto errorDto = createErrorDto(status.value(), INVALIDATED_PROPERTY, error.getMessage())
                .userMessage(ERROR_MESSAGE_BAD_REQUEST)
                .fields(fields)
                .build();

        return super.handleExceptionInternal(error, errorDto, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        /* Provided by a dependency commons-lang3 */
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause,
                    headers, status, request);
        }

        ErrorDto errorDto = createErrorDto(status.value(),
                ENTITY_CANNOT_BE_PROCESSED, ex.getMessage()).build();

        return handleExceptionInternal(ex,
                errorDto,
                headers,
                status,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        boolean isPresentAndNotaString = nonNull(body) && body instanceof String == FALSE;

        if (isPresentAndNotaString) {
            return super.handleExceptionInternal(ex, body, headers, status, request);
        }

        body = ErrorDto.builder()
                .title(isPresentAndNotaString == FALSE ? status.getReasonPhrase() : (String) body)
                .status(status.value())
                .dateTime(now())
                .build();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldErrorDto> fields = getFieldsErrors(ex.getBindingResult());

        ErrorDto errorDto = createErrorDto(status.value(), INVALIDATED_PROPERTY, ex.getMessage())
                .userMessage(ERROR_MESSAGE_BAD_REQUEST)
                .fields(fields)
                .build();

        return super.handleExceptionInternal(ex, errorDto, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String pathProperty = ex.getPath()
                .stream()
                .map(path -> path.getFieldName())
                .collect(Collectors.joining("."));

        String errorMessage = format("The property '%s' received a value of '%s' " +
                "and unfortunately is wrong for the type '%s'", pathProperty, ex.getValue(),
                ex.getTargetType().getSimpleName());

        ErrorDto errorDto = createErrorDto(status.value(),
                UNREADABLE_BODY, errorMessage).build();

        return handleExceptionInternal(ex, errorDto, headers, status, request);
    }

    private ErrorDto.ErrorDtoBuilder createErrorDto(Integer status, ProblemType problemType,
                                                    String detail) {
        return ErrorDto.builder()
                .title(problemType.getTitle())
                .type(problemType.getUri())
                .status(status)
                .detail(detail)
                .userMessage("System unavailable, please try again later");
    }

    private String getPropertyNameFromError(ObjectError objectError) {
        if (isNull(objectError)) {
            return EMPTY;
        }

        return objectError instanceof FieldError ?
                ((FieldError) objectError).getField() : objectError.getObjectName();
    }

    private List<FieldErrorDto> getFieldsErrors(BindingResult bindingResult) {
        if (isNull(bindingResult)) {
            return List.of();
        }

        return bindingResult.getAllErrors()
                .stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    return FieldErrorDto.builder()
                            .name(getPropertyNameFromError(objectError))
                            .userMessage(message)
                            .build();
                }).collect(Collectors.toList());
    }
}
