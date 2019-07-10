package com.filipe.domain.enums;

/**
 * Enumeração que define o tipo de Cliente. 
 * No banco de dados a enumeração pode ser salva como um número Inteiro ou como uma String.
 * Essa enumeração será salva como um número Inteiro no banco.
 * */
public enum TipoCliente {
	
	PESSOAFISICA(0,"Pessoa Física"),
	PESSOAJURIDICA(1, "Pessoa Jurídica");
	
	private Integer cod;
	private String descricao;
	
	private TipoCliente(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	//Método que converte um código inteiro no enum equivalente. Lança uma exceção caso o código inválido.
	public static TipoCliente toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("código inválido: "+ cod);
	}
	
}
