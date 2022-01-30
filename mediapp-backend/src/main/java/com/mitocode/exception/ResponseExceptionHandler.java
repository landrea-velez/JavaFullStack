package com.mitocode.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> manejarOtrasExcepciones(ModeloNotFoundException ex, WebRequest request) {
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(ModeloNotFoundException.class)
	public ResponseEntity<ExceptionResponse> manejarModeloNotFounException(ModeloNotFoundException ex,
			WebRequest request) {
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String message = ex.getBindingResult().getAllErrors().stream().map(e -> e.getDefaultMessage().concat(", "))
				.collect(Collectors.joining());

		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), message,
				request.getDescription(false));
		return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
	}

}
