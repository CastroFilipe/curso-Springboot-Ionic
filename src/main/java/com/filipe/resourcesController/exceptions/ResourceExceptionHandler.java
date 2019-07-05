package com.filipe.resourcesController.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.filipe.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	//classe para manipular as exceções lançadas ao pacote resources
	
	//indica que é um tratador de exceções do tipo ObjectNotFoundException
	@ExceptionHandler(ObjectNotFoundException.class)
	
	//Recebe um ObjectNotFoundException e um request(as informações da requisição). Parametros padrões do framework
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, 
			HttpServletRequest request){
		
		//criar um objeto padrão err com o Status 404, uma mensagem e o momento da ocorrencia
		StandardError err = new StandardError(
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(),
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
