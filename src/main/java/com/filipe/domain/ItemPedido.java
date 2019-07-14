package com.filipe.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A classe ItemPedido é uma classe de Associação para relacionamentos ManyTOMany.
 * Um Pedido pode contar um ou mais produtos e um produto pode estar contido em muitos
 * pedidos.
 * 
 * A classe ItemPedido não deverá conter um Id próprio e sim um id composto representado 
 * pela classe ItemPedidoPK. 
 * Sua identificação se dará através dos dois objetos associados a ela que são o
 * Produto e o Pedido presentas na classe ItemPedidoPK 
 * 
 * */
@Entity
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*Um objeto ItemPedidoPK que representa a chave primaria composta de um ItemPedido.
	 * A chave contém o Produto e o Pedido vinculados.
	 * 
	 * @EmbeddedId indica que é um id embutido em um tipo auxiliar
	 * */
	@EmbeddedId
	@JsonIgnore //Será ignorado na serialização, também evitará serialização cíclica do Json
	private ItemPedidoPK id = new ItemPedidoPK();
	
	private Double desconto;
	private Integer quantidade;
	private Double preco;
	
	public ItemPedido() {
		
	}

	/*Os parametros pedido e produto vindos para o construtor serão adicionados
	 * aos atributos internos do objeto id ItemPedidoPK
	 * */
	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		this.id.setPedido(pedido);//adicionando o pedido ao id
		this.id.setProduto(produto);//adicionando o produto ao id
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	/**Faz o cálculo do subtotal 
	 * Ex: Comprar duas TVs de 40 polegadas, R$ 1000 cada sem descontos.
	 * Subtotal = R$ 2000
	 * */
	public double getSubTotal() {
		return (preco - desconto) * quantidade;
	}
	
	@JsonIgnore//Será ignorado na serialização, também evitará serialização cíclica do Json
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
	
	public Produto getProduto() {
		return id.getProduto();
	}
	
	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}
	
	public ItemPedidoPK getId() {
		return id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
