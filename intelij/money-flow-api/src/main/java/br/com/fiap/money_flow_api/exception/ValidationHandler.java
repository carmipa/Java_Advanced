package br.com.fiap.money_flow_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationHandler {

    record ValidationError(String field, String message){
        public ValidationError(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    // executar em caso de erro de validação
    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public List<ValidationError> handle(MethodArgumentNotValidException e) {

        // return new ValidationError(e.getFieldError().getField(), e.getFieldError().getDefaultMessage()).toString();
        return e.getFieldErrors().stream()
                .map(ValidationError::new)
                .toList();
    }
}
