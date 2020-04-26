package ecommerce.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ecommerce.exception.FileException;
import ecommerce.exception.ResourceNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ApiError.Field> fields = ex.getBindingResult().getAllErrors().stream().map(objectError -> {
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			String name = ((FieldError) objectError).getField();
			return new ApiError.Field(name, message);
		}).collect(Collectors.toList());
		String message = "Um ou mais campos estão inválidos.";
		ApiError apiError = new ApiError(status.value(), message, OffsetDateTime.now(), fields);
		return new ResponseEntity<>(apiError, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = "O corpo da requisição está inválido.";
		ApiError apiError = new ApiError(status.value(), message, OffsetDateTime.now());
		return new ResponseEntity<>(apiError, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		List<ApiError.Field> fields = ex.getBindingResult().getAllErrors().stream().map(objectError -> {
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			String name = ((FieldError) objectError).getField();
			return new ApiError.Field(name, message);
		}).collect(Collectors.toList());
		String message = "Um ou mais campos estão inválidos.";
		ApiError apiError = new ApiError(status.value(), message, OffsetDateTime.now(), fields);
		return new ResponseEntity<>(apiError, headers, status);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	private ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), OffsetDateTime.now());
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FileException.class)
	private ResponseEntity<ApiError> handleFileException(FileException ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
				OffsetDateTime.now());
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
