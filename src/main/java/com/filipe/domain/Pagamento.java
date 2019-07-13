package com.filipe.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filipe.domain.enums.EstadoPagamento;

/**
 * Classe que define um pagamento. Essa classe será usada como Super Classe das 
 * classes PagamentoComCartao e PagamentoComBoleto.
 * Logo os atributos id, estadoPagamento e pedido serão herdados nas subclasses. 
 * 
 * @Entity indica ao JPA que essa classe é uma Entidade. Assim o hibernate fará a persistência da
 * classe no banco.
 * 
 * @Inheritance Anotação usada em uma super Classe. A anotação garante que qualquer subclasse
 * que herde de Pagamento será salva no banco de dados de acordo com o tipo strategy.
 * Para @InheritanceType.JOINED cada uma das subclasses(PagamentoComBoleto e PagamentoComCartao)
 * terá sua própria tabela no banco de dados. Essa é a estratégia mais adotada.
 * 
 * Para @InheritanceType.SINGLE_TABLE todas as subclasses que herdem de Pagamento serão salvas
 * num único "Tabelão" no banco de dados. Se necessário salvar um PagamentoComBoleto, por exemplo,
 * será preciso definir as colunas referentes a PagamentoComCartao como null no banco de dados e 
 * vice-versa.
 * 
 * A classe será abstrata para não permitir a instanciação de objetos do tipo Pagamento. 
 * Para instanciar utilize as subclasses PagamentoComBoleto e PagamentoComCartao
 * */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;

	private Integer estadoPagamento;
	
	/**
	 * @MapsId o id da classe Pagamento será o mesmo do pedido correspondente. Assim a classe 
	 * Pagamento possui um id que será igual ao id da classe Pagamento. Por essa razão omitimos
	 * a declaração @GeneratedValue(strategy = GenerationType.IDENTITY) no id dessa classe pois o
	 * mesmo será gerado a partir do id do Pedido.
	 * 
	 * O id_pedido também será o id das classes PagamentoComBoleto e PagamentoComCartao 
	 * que herdam de Pagamento. 
	 * */
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId
	private Pedido pedido;
	
	public Pagamento() {
	}

	public Pagamento(Integer id, EstadoPagamento estadoPagamento, Pedido pedido) {
		super();
		this.id = id;
		this.estadoPagamento = (estadoPagamento == null) ? null : estadoPagamento.getCod();
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public EstadoPagamento getEstadoPagamento() {
		return EstadoPagamento.toEnum(estadoPagamento);
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
		this.estadoPagamento = estadoPagamento.getCod();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pagamento [id=" + id + ", estadoPagamento=" + estadoPagamento + ", pedido=" + pedido + "]";
	}

}
