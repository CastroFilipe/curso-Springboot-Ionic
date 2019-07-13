package com.filipe.resourcesController.exceptions;

import java.io.Serializable;
/**
 * Classe auxiliar que representa um objeto com campo e mensagem. A classe é usada quando uma
 * exceção do tipo MethodArgumentNotValidException for lançada. Essa exceção se dá quando um 
 * atributo vindo da view não atende os requisitos de validações feitas com o Bean Validator nas
 * classes DTO.
 * 
 * A classe FieldMessage será usada para enviar o nome do atributo que gerou o erro e 
 * a mensagem padrão desse atributo que foi definida no DTO
 * */
public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String message;

	public FieldMessage() {
	}

	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}