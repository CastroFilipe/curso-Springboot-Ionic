package com.filipe.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

/**
 * Classe que representa um Produto
 * 
 * @Entity indica ao JPA que essa classe é uma Entidade. Assim o hibernate fará a persistência da
 * classe no banco.
 * 
 * @Data: Anotação do pacote lombok que gera getters e setters, hashCode and equals, 
 * toString entre outros. Fazer testes e checar as funcionalidades.
 * */
@Data
@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;

	/**
	 * JsonBackReference: Cada entidade do tipo Categoria tem uma coleção de Produtos 
	 * e cada entidade do tipo Produto tem uma coleção de categorias.  Para evitar uma 
	 * referência cíclica em relacionamentos ManyToMany  as anotações  JsonBackReference 
	 * e JsonManagedReference devem ser utilizadas em conjunto.  
	 * 
	 * JoinTable: Anotação necessária para criar uma tabela intermediaria entre duas 
	 * entidades que possuem relacionamento ManyToMany.
	 * 
	 */
	@JsonBackReference
	@ManyToMany
	@JoinTable(
			name = "PRODUTO_CATEGORIA", 
			joinColumns = @JoinColumn(name = "produto_id"), 
			inverseJoinColumns = @JoinColumn(name = "categoria_id")
			)
	private List<Categoria> categorias = new ArrayList<Categoria>();

	public Produto() {
	}

	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
