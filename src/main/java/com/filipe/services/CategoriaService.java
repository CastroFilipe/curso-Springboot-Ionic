package com.filipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipe.domain.Categoria;
import com.filipe.repositories.CategoriaRepository;
import com.filipe.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;

	/**
	 * Método que busca uma Categoria por ID, caso não encontre Lança uma exceção 
	 * personalizada (Uma exceção criada pelo programador).
	 * */
	public Categoria buscarPorId(Integer id) {
		
		/**
		 * findById() retorna um Objeto Optional.
		 * Um Objeto Optional funciona como um container na qual o Objeto Categoria pode ou
		 * não estar contido.
		 * 
		 * O uso de Optional evitará nullPointerException pois se o Objeto não for encontrado
		 * retornará um Optional vazio ao invés de um null.
		 * */
		Optional<Categoria> obj = repo.findById(id);
		
		/**
		 * Retorna o objeto Categoria presente dentro do "container" Optional.
		 * Caso não exista o objeto categoria, devido a negativa da busca anterior, lançará uma exceção.
		 * A exceção será lançada para a classe que chamou o metodo buscarPorId().
		 * 
		 * ObjectNotFoundException é uma exceção personalizada criada no pacote services.exceptions
		 * */

		return obj.orElseThrow(()->new ObjectNotFoundException(
				"Objeto não Encontrado! Id: " + id + " ,tipo:"+ Categoria.class.getName()));
	}
}
