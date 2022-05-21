package com.supermarketapi.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> itemNotFound(ItemNotFoundException exception) {
        return errorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorResponse> notEnoughMoney(NotEnoughMoneyException exception) {
        return errorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> purchaseNotFound(PurchaseNotFoundException exception) {
        return errorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SupermarketAlreadyExists.class)
    public ResponseEntity<ErrorResponse> supermarketAlreadyExists(SupermarketAlreadyExists exception) {
        return errorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SupermarketNotFoundException.class)
    public ResponseEntity<ErrorResponse> purchaseNotFound(SupermarketNotFoundException exception) {
        return errorResponse(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> errorResponse(RuntimeException exception, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(httpStatus.value());
        error.setMessage(exception.getMessage());
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, httpStatus);
    }


    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        return new ResponseEntity<>(getFieldErrors(ex, status), headers, status);
    }

    private Map<String, Object> getFieldErrors(MethodArgumentNotValidException ex, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        Map<String, List<String>> fieldErrors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String key = error.getField();

            if (!fieldErrors.containsKey(key)) {
                fieldErrors.put(key, new ArrayList<>());
            }

            fieldErrors.get(key).add(error.getDefaultMessage());
        }

        body.put("errors", fieldErrors);

        return body;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex) {
        return new ResponseEntity<>(getBody(ex), new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> getBody(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Something Went Wrong");
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        body.put("exception", ex.toString());

        Throwable cause = ex.getCause();
        if (cause != null) {
            body.put("exceptionCause", ex.getCause().toString());
        }

        return body;
    }
}
