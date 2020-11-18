package com.deltegui.plantio;

import com.deltegui.plantio.common.DomainError;
import com.deltegui.plantio.common.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DomainExceptionHandler {
    public static class SerializableDomainError implements DomainError {
        private DomainError innerError;

        public SerializableDomainError(DomainError innerError) {
            this.innerError = innerError;
        }

        @Override
        public int getCode() {
            return innerError.getCode();
        }

        @Override
        public String getMessage() {
            return innerError.getMessage();
        }
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DomainError handleDomainException(DomainException exception, HttpServletResponse response) {
        return new SerializableDomainError(exception.getError());
    }

    public static class ValidationError implements DomainError {
        private final Map<String, String> errors;

        private ValidationError(Map<String, String> errors) {
            this.errors = errors;
        }

        public static ValidationError fromException(MethodArgumentNotValidException exception) {
            var errors = new HashMap<String, String>();
            for (var error : exception.getBindingResult().getAllErrors()) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            }
            return new ValidationError(errors);
        }

        @Override
        public int getCode() {
            return 1;
        }

        @Override
        public String getMessage() {
            return "Validation Error";
        }

        public Map<String, String> getErrors() {
            return this.errors;
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationError handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        return ValidationError.fromException(exception);
    }
}
