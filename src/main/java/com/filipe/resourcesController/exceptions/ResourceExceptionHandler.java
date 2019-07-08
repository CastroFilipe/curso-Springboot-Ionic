package com.filipe.resourcesController.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.filipe.services.exceptions.ObjectNotFoundException;

/**
 * A anotação @ControllerAdvice indica que os métodos desta classe serão "Compartilhados"
 * com as classes que possuem a anotação @Controller ou @RestController 
 * 
 * A classe ResourceExceptionHandler interceptará qualquer exceção lançada no pacote 
 * resource/controller. As exceções serão tratadas nessa classe evitando assim o 
 * uso exagerado de blocos Try Catch nas classes do pacote resource/controller
 * 
 * */
@ControllerAdvice
public class ResourceExceptionHandler {

	/**
	 * @ExceptionHandler: Indica que o método objectNotFound() será chamado sempre que
	 * uma exceção do tipo ObjectNotFoundException surgir no pacote resource/controller.
	 * 
	 * @param e exceção do tipo ObjectNotFoundException lançada no pacote controller
	 * @param request objeto do tipo HttpServletRequest contendo as informações da 
	 * requisição.
	 * 
	 * @return um Objeto ResponseEntity com o código do erro e o objeto StandardError
	 * no corpo(body) da resposta
	 * */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, 
			HttpServletRequest request){
		
		//instanciará um objeto de erro padrão(StandardError) com o Status 404, uma mensagem e o momento da ocorrencia
		StandardError err = new StandardError(
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(),
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
