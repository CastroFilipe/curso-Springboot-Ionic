package com.filipe.domain;

import javax.persistence.Entity;

import com.filipe.domain.enums.EstadoPagamento;

/**
 * Classe que herda de Pagamento. Contém os atributos id, estadoDoPagamento, 
 * pedido e numeroDeParcelas.
 * 
 * @Entity indica ao JPA que essa classe é uma Entidade. Assim o hibernate fará a persistência da
 * classe no banco.
 * */
@Entity
public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;

	public PagamentoComCartao() {
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estadoPagamento, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estadoPagamento, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
}
