package com.filipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.filipe.domain.Categoria;
import com.filipe.repositories.CategoriaRepository;
import com.filipe.services.exceptions.DataIntegrityException;
import com.filipe.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;

	/**
	 * Método que busca uma Categoria por ID, caso não encontre Lança uma exceção 
	 * personalizada (Uma exceção criada pelo programador).
	 * */
	public Categoria find(Integer id) {
		
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
	
	/**
	 * Método para inserir uma objeto no banco de dados.
	 * 
	 * @param obj um objeto a ser inserido
	 * */
	public Categoria insert(Categoria obj) {
		/*
		 * Garante que qualquer categoria a ser inserida tenha o id nulo pois,
		 * quando o id não for nulo, o objeto categoria estará sendo atualizado e 
		 * não inserido.
		 * 
		 * */
		obj.setId(null);
		
		/*
		 * Salva o objeto e retorna o para o controller o própio Objeto.
		 * */
		return repo.save(obj);
	}
	
	/**
	 * Método para atualizar um objeto no banco de dados.
	 * 
	 * @param obj o objeto a ser atualizado
	 * */
	public Categoria update(Categoria obj) {
		/*Antes de atualizar fazer uma busca no banco para garantir que o objeto exista no banco
		 * Se não exixtir será lançada a exceção no método buscarPorId*/
		find(obj.getId());
		
		/*O método save é usado tanto para inserir quanto para atualizar.
		 * Isso é decidido de acordo com o ID. Se o id for nulo ele irá inserir o objeto
		 * se o id não for nulo, o objeto será atualizado*/
		return repo.save(obj);
	}

	public void delete(Integer id) {
		/*Verificando se o objeto a ser excluido realmente existe no banco de dados*/
		find(id);
		
		/*faz a exclusão pelo id. Pode lançar a exceção DataIntegrityViolationException
		 * caso o objeto seja referenciado em outras tabelas(integridade referêncial) */
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e){
			
			/*lança a exceção personalizada que será capturada no pacote controller */
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
}
