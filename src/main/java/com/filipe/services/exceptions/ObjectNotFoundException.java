package com.filipe.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String descricao) {
		super(descricao);
	}
	
	//Throwable a causa de uma exceção que aconteceu antes
	public ObjectNotFoundException(String descricao, Throwable cause) {
		super(descricao, cause);
	}
}
