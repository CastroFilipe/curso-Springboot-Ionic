package com.filipe.resourcesController.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.filipe.services.exceptions.DataIntegrityException;
import com.filipe.services.exceptions.ObjectNotFoundException;

/**
 * A anotação @ControllerAdvice indica que os métodos desta classe serão
 * "Compartilhados" com as classes que possuem a anotação @Controller
 * ou @RestController
 * 
 * A classe ResourceExceptionHandler interceptará qualquer exceção lançada no
 * pacote resource/controller. As exceções serão tratadas nessa classe evitando
 * assim o uso exagerado de blocos Try Catch nas classes do pacote
 * resource/controller
 * 
 */
@ControllerAdvice
public class ResourceExceptionHandler {

	/**
	 * Método chamado quando uma exceção do tipo ObjectNotFound for lançada nas
	 * classes do pacote controller.
	 * 
	 * @param e       exceção do tipo ObjectNotFoundException lançada no pacote
	 *                controller
	 * @param request objeto do tipo HttpServletRequest contendo as informações da
	 *                requisição.
	 * 
	 * @return um Objeto ResponseEntity com o código do erro e o objeto
	 *         StandardError no corpo(body) da resposta
	 */
	/*
	 * @ExceptionHandler: Indica que o método objectNotFound() será chamado sempre
	 * que uma exceção do tipo ObjectNotFoundException surgir no pacote
	 * resource/controller.
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		// instanciará um objeto de erro padrão(StandardError) com o Status 404, uma
		// mensagem e o momento da ocorrencia
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	/**
	 * Método chamado quando uma exceção do tipo DataIntegrityException for lançada
	 * nas classes do pacote controller.
	 * 
	 * @param e       exceção do tipo DataIntegrityException lançada no pacote
	 *                controller
	 * @param request objeto do tipo HttpServletRequest contendo as informações da
	 *                requisição.
	 * 
	 * @return um Objeto ResponseEntity com o código do erro e o objeto
	 *         StandardError no corpo(body) da resposta
	 */
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

		/*
		 * instanciará um objeto de erro padrão(StandardError) com o Status 400(BAD
		 * REQUEST), uma mensagem e o momento da ocorrência
		 */
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	/**
	 * Método chamado quando uma exceção do tipo MethodArgumentNotValidException for
	 * lançada nas classes do pacote controller. Essa exceção se dá quando um
	 * atributo vindo da view não atende os requisitos de validações feitas com o
	 * Bean Validator nas classes DTO.
	 * 
	 * @param e       exceção do tipo MethodArgumentNotValidException lançada no
	 *                pacote controller
	 * @param request objeto do tipo HttpServletRequest contendo as informações da
	 *                requisição.
	 * 
	 * @return um Objeto ResponseEntity com o código do erro e o objeto
	 *         ValidationError no corpo(body) da resposta
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				System.currentTimeMillis());
		/*
		 * O objeto err do tipo ValidationError possui uma lista<FieldMessage>. O for
		 * abaixo adiciona o par (ATRIBUTO, Mensagem de erro) na lista de erros do
		 * objeto err
		 */
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
