package com.algaworks.osworks.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.osworks.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request, HttpStatus status) {
		Problema problema = toEntity(status, ex.getMessage());
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> EntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request,
			HttpStatus status) {
		Problema problema = toEntity(status, ex.getMessage());

		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Problema.Campos> campos = new ArrayList<Problema.Campos>();
		Problema problema = toEntityColections(status,
				"Um ou mais campo estao invalidos. faca o prenchimento correto  e tente novamente", campos, ex);
		problema.setCampos(campos);
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	public Problema toEntity(HttpStatus status, String title) {
		HttpStatus StatusHttp = status;
		Problema problema = new Problema();
		problema.setStatus(StatusHttp.value());
		problema.setTitulo(title);
		problema.setDataHora(OffsetDateTime.now());
		return problema;
	}

	public Problema toEntityColections(HttpStatus status, String title, List<Problema.Campos> campos,
			MethodArgumentNotValidException ex) {
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			String nome = ((FieldError) error).getField();
			campos.add(new Problema.Campos(nome, mensagem));
		}

		HttpStatus StatusHttp = status;
		Problema problema = new Problema();
		problema.setStatus(StatusHttp.value());
		problema.setTitulo(title);
		problema.setDataHora(OffsetDateTime.now());
		return problema;
	}

}
