package com.filipe.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filipe.domain.enums.TipoCliente;

/**
 * Classe que define um Cliente.
 * A classe contém um conjunto Set de telefones e uma Lista List de Enderecos.
 * 
 * @Entity indica ao JPA que essa classe é uma Entidade. Assim o hibernate fará a persistência da
 * classe no banco.
 * */
@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String cpfOuCnpj;
	
	/*
	 * O TipoCliente será salvo no banco de dados como um número inteiro.
	 * 0 para PESSOAFISICA e 1 para PESSOAJURIDICA. 
	 * Nos métodos getters e setters e no construtor serão utilizados métodos para converter 
	 * um TipoPessoa para o inteiro equivalente e vice-versa. 
	 * */
	private Integer tipo;
	
	/*
	 * O Conjunto de telefones é uma "entidade fraca", não criamos uma classe no pacote model
	 * que represente um telefone. O mapeamento será feito a partir das anotações 
	 * @ElementCollection e @CollectionTable
	 * Logo a tabela de telefones no banco de dados não conterá um id próprio e sim o id do cliente.
	 * 
	 * */
	@ElementCollection
	@CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>();
	
	@OneToMany(mappedBy = "cliente")
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	
	/*
	 * 
	 * JsonIgnore: Cada Cliente tem uma Lista de Pedidos e cada Pedido tem um Cliente. 
	 * Para evitar uma referência cíclica a anotação JsonIgnore é usada.
	 * 
	 * */
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() {
	}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		
		//O consturtor recebe um TipoCliente como parametro, porém devemos salvar apenas o inteiro equivalente.
		this.tipo = (tipo == null) ? null : tipo.getCod();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	/*
	 * o atributo tipo da classe Cliente é um Inteiro. Por isso no método getTipo que retorna
	 * um TipoCliente será feito a conversão do Inteiro tipo para o enum TipoCliente equivalente.
	 * */
	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	/*
	 * O método setTipo recebe um Objeto enum TipoCliente como parametro. Todavia apenas o 
	 * código da do Objeto enum deve ser salvo. O Método getCod() retorna apenas o código 
	 * Inteiro do parametro recebido.
	 * */
	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + "]";
	}
}
