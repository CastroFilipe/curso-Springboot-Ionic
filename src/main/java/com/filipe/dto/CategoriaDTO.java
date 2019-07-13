package com.filipe.dto;

import java.io.Serializable;

import com.filipe.domain.Categoria;
/**
 * Objeto DTO usado para exibir apenas as informações necessárias a view.
 * 
 * Diferente da classe Categoria que possui os atributos id, nome e uma lista de produtos, a classe 
 * CategoriaDTO possui apenas id e nome pois não queremos que a lista de produtos seja exibida
 * no Json retornado em respostas Http.
 * 
 * A classe CategoriaDTO funcionará com uma classe mais "Slim" da classe Categoria.
 * */
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
	}
	
	/*Construtor que cria um objeto CategoriaDTO a partir de um objeto Categoria 
	 * passado como parametro*/
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
