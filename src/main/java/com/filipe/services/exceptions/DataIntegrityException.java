package com.filipe.services.exceptions;


/**
 * Exceção personalizada que herda da classe RuntimeException e que será lançada quando 
 * um objeto não puder ser excluído do banco de dados devido a integridade referencial.
 * O objeto não será excluído pois sua chave primária é referenciada em outras tabelas.
 * */
public class DataIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String descricao) {
		super(descricao);
	}
	
	//Throwable a causa de uma exceção que aconteceu antes
	public DataIntegrityException(String descricao, Throwable cause) {
		super(descricao, cause);
	}
}
