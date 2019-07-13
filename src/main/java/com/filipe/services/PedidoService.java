package com.filipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipe.domain.Pedido;
import com.filipe.repositories.PedidoRepository;
import com.filipe.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	/**
	 * Método que busca um objeto por ID, caso não encontre Lança uma exceção 
	 * personalizada do tipo ObjectNotFoundException. Para mais informações consultar o mesmo
	 * método na classe @Categoria
	 * 
	 * @param id o Id do objeto buscado.
	 * 
	 * @throws ObjectNotFoundException se não encontrar o objeto no banco de dados.
	 * */
	public Pedido find(Integer id) {
		
		/*
		 * findById() retorna um Objeto Optional.
		 * Um Objeto Optional funciona como um container na qual o Objeto Pedido pode ou
		 * não estar contido.
		 * 
		 * O uso de Optional evitará nullPointerException pois se o Objeto não for encontrado
		 * retornará um Optional vazio ao invés de um null.
		 * */
		Optional<Pedido> obj = repo.findById(id);
		
		/*
		 * Retorna o objeto Pedido presente dentro do "container" Optional.
		 * Caso não exista o objeto Pedido, devido a negativa da busca anterior, lançará uma exceção.
		 * A exceção será lançada para a classe que chamou o metodo find()
		 * 
		 * ObjectNotFoundException é uma exceção personalizada criada no pacote services.exceptions
		 * */

		return obj.orElseThrow(()->new ObjectNotFoundException(
				"Objeto não Encontrado! Id: " + id + " ,tipo:"+ Pedido.class.getName()));
	}
}
