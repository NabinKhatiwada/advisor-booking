package com.booking.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.booking.util.MyConstants;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
//	 private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// 400: Bad Request => Arguments Invalid Exception
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleMethodArgumentNotValid(HttpMessageNotReadableException exception,
			WebRequest request) {
//			Map<String, String> errors = new HashMap<>();
//
//			exception.getBindingResult().getFieldErrors()
//					.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		ErrorDetails errorDetails = new ErrorDetails(exception.getMostSpecificCause().getMessage(),
				request.getDescription(false));
		logger.log(Level.ERROR,"Arguments Invalid Exception ", exception);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// 400: Bad Request => Arguments Invalid Exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			WebRequest request) {
		Map<String, String> errors = new HashMap<>();

		exception.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		ErrorDetails errorDetails = new ErrorDetails(errors, request.getDescription(false));
		logger.log(Level.ERROR,"Arguments Invalid Exception ", exception);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// 400 : BAD Request => handling specific exception
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> badRequestHandling(BadRequestException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), request.getDescription(false));
		logger.log(Level.ERROR,"BadRequestException Exception ", exception);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	// 404 : NOT Found => handling specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), request.getDescription(false));
		logger.log(Level.ERROR,"BadRequestException Exception ", exception);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// 409 : CONFLICT => handling specific exception
	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<?> alreadyExistHandling(AlreadyExistException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), request.getDescription(false));
		logger.log(Level.ERROR,"Resource Already Exist ", exception);
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}

	// handling specific exception
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> customExceptionHandling(CustomException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), request.getDescription(false));
		logger.log(Level.ERROR,"CustomException ", exception);
		return new ResponseEntity<>(errorDetails, exception.getHttpStatus());
	}

	// handling global exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(MyConstants.MSG_SYSTEM_ERROR, request.getDescription(false));
		logger.log(Level.FATAL,"Global Exception ", exception);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}