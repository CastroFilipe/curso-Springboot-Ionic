package com.filipe.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.filipe.domain.enums.EstadoPagamento;

/**
 * Classe que herda de Pagamento. Contém os atributos id, estadoDoPagamento, 
 * pedido, dataVencimento e dataPagamento.
 * 
 * @Entity indica ao JPA que essa classe é uma Entidade. Assim o hibernate fará a persistência da
 * classe no banco.
 * 
 * @JsonTypeName Informa que o identificador pagamentoComBoleto representa essa classe.
 * Assim um objeto PagamentoComBoleto poderá ser criado quando as informações Json vindas da view 
 * contiverem o "@type = "pagamentoComCartao"". 
 * */
@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	/*
	 * @JsonFormat formata o instante para o padrão especificado. 
	 * Se não for formatado a dataVencimento conterá os milisegundos desde 1970
	 * */
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataVencimento;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
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
