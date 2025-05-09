package com.retail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TransactionNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleCustomerNotFound(TransactionNotFoundException ex) {
		return new ResponseEntity<>(
				Map.of("timestamp", LocalDateTime.now(), "error", "Customer Not Found", "message", ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		return new ResponseEntity<>(
				Map.of("timestamp", LocalDateTime.now(), "error", "Internal Server Error", "message", ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return new ResponseEntity<>(
				Map.of("timestamp", LocalDateTime.now(), "error", "Bad Request", "message",
						"Invalid input for parameter '" + ex.getName() + "': " + ex.getValue()),
				HttpStatus.BAD_REQUEST);
	}
}
