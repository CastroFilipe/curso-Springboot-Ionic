package com.filipe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Classe que define um pedido.
 * 
 * @Entity indica ao JPA que essa classe é uma Entidade. Assim o hibernate fará a persistência da
 * classe no banco.
 * */
@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	/*
	 * @JsonFormat formata o instante para o padrão especificado. 
	 * Se não for formatado o instante conterá os milisegundos desde 1970
	 * */
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date instante;
	
	/**
	 *  A função do cascade é cascatear operações de persistência.
	 *  Realizar operações em cascata só faz sentido em relacionamentos Pai - Filho 
	 *  (a transição do estado da entidade Pai sendo realizada em cascata na entidade Filho).
	 *  
	 *  CascadeType.ALL executa todas as operações de cascade(Exclusão, atualização, etc).
	 *  Usamos o CascadeType pois utilizamos o @MapsId na classe Pagamento.
	 * */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
	private Pagamento pagamento;
	
	/*Um pedido pertence a um único cliente e um cliente pode ter mais de um pedido*/
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	
	/*Um pedido possui apenas um endereço de entrega, todavia vários pedidos podem ser 
	 * entregues no mesmo endereço*/
	@ManyToOne
	@JoinColumn(name="endereco_entrega_id")
	private Endereco enderecoDeEntrega;
	
	/*A entidade Pedido deve conhecer a entidade de Ligação ItemPedido
	 * O mapeamento se dá no atributo pedido declarado na classe ItemPedidoPK.
	 * Logo usaremos (mappedBy = "id.pedido")
	 * */
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Pedido() {
	}
	
	public Pedido(Integer id, Date instante, Pagamento pagamento, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.pagamento = pagamento;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	
	/**
	 * Cálculo do valor total de um pedido.
	 * Cálculo feito através da soma do valor de cada ItemPedido.
	 * 
	 * @return o valor total do pedido.
	 * */
	public double getValorTotal() {
		double soma = 0.0;
		for (ItemPedido ip : itens) {
			soma = soma + ip.getSubTotal();
		}
		return soma;
	}


	public Integer getId() {
		return id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + "]";
	}
}
