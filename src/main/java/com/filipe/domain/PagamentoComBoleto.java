package com.filipe.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.filipe.domain.enums.EstadoPagamento;

/**
 * Classe que herda de Pagamento. Contém os atributos id, estadoDoPagamento, 
 * pedido, dataVencimento e dataPagamento.
 * 
 * @Entity indica ao JPA que essa classe é uma Entidade. Assim o hibernate fará a persistência da
 * classe no banco.
 * */
@Entity
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Date dataVencimento;
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento estadoPagamento, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estadoPagamento, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	
}
