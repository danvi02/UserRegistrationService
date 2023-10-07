package com.user.registration.api.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegistrationServiceExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException argumentNotValidException) {
		List<String> errorList = new ArrayList<>();
		argumentNotValidException.getBindingResult()
																.getFieldErrors()
																.forEach(objectError -> errorList.add(objectError.getDefaultMessage()));
		ErrorResponse response = ErrorResponse.builder()
									.timestamp(LocalDateTime.now().toString())
									.developerMessage(errorList.toString())
									.exceptionDetails(argumentNotValidException.getLocalizedMessage())
									.build();
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}

}
