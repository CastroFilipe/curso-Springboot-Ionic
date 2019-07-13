package com.filipe.resourcesController.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um erro de validação. Contém todos os atributos da classe de erro padrão StandardError
 * e ainda contém uma lista de FiledMessage. Essa classe será usada ao invés do standardError 
 * para exibir todos os erros  de validação que aconteceram em um atributo de alguma classe DTO.
 * */

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
}
