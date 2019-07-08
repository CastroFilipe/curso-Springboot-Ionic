package com.filipe.services.exceptions;


/**
 * Exceção personalizada que herda da classe RuntimeException e que será lançada quando 
 * um método não encontrar um determinado Objeto após fazer uma busca no banco de dados.
 * 
 * essa exceção poderá ser usada sempre que um Objeto não for encontrado
 * */
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
