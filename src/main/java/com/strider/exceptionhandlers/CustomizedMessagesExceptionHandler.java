package com.strider.exceptionhandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.strider.exception.RegraNegocioException;
import com.strider.exception.SampleEntityNotFoundException;
import com.strider.response.MessageType;
import com.strider.response.ServiceMessage;
import com.strider.response.ServiceResponse;
import com.strider.service.MessagesService;


@ControllerAdvice
public class CustomizedMessagesExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessagesService messages;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(
				new ServiceResponse<>(
						new ServiceMessage(MessageType.ERROR, messages.get("global.error.json.malFormatado"))),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder messagesValidation = new StringBuilder();
		ex.getBindingResult().getFieldErrors()
				.forEach(item -> messagesValidation.append(item.getDefaultMessage() + ";"));
		String[] messagesArray = messagesValidation.toString().split(";");
		List<String> mensagensList = Arrays.asList(messagesArray);
		List<ServiceMessage> serviceMessages = new ArrayList<>();
		mensagensList.forEach(s -> serviceMessages.add(new ServiceMessage(MessageType.WARN, s)));

		return new ResponseEntity<>(new ServiceResponse<>(serviceMessages), HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(
				new ServiceResponse<>(new ServiceMessage(MessageType.ERROR, messages.get("global.error.mediaType"))),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(
				new ServiceResponse<>(new ServiceMessage(MessageType.ERROR, messages.get("global.error.interno"))),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SampleEntityNotFoundException.class)
	public ResponseEntity<Object> handleSampleEntityNotFoundException(SampleEntityNotFoundException ex,
			WebRequest request) {
		return new ResponseEntity<>(new ServiceResponse<>(new ServiceMessage(MessageType.ERROR, ex.getMessage())),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> entidadeNaoEcontrada(IllegalArgumentException ex, WebRequest request) {
		return new ResponseEntity<>(new ServiceResponse<>(new ServiceMessage(MessageType.ERROR, ex.getMessage())),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RegraNegocioException.class)
	public final ResponseEntity<ServiceResponse<Void>> validaNegocio(RegraNegocioException ex, WebRequest request) {
		final String REGEX = ";";
		List<ServiceMessage> serviceMessages = new ArrayList<>();
		String errorStack = ex.getMessage();
		if (errorStack.contains(REGEX)) {
			String[] mensagens = errorStack.split(REGEX);
			List<String> msgList = Arrays.asList(mensagens);
			msgList.forEach(s -> serviceMessages.add(new ServiceMessage(MessageType.WARN, s)));
		} else {
			serviceMessages.add(new ServiceMessage(MessageType.WARN, errorStack));
		}
		return new ResponseEntity<>(new ServiceResponse<>(serviceMessages), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> tratarTodas(Exception ex, WebRequest request) {
		return new ResponseEntity<>(new ServiceResponse<>(new ServiceMessage(MessageType.ERROR, ex.getMessage())),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
