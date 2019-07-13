package com.filipe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
	 * Método que busca um objeto por ID, caso não encontre Lança uma exceção 
	 * personalizada do tipo ObjectNotFoundException.
	 * 
	 * @param id o Id do objeto buscado.
	 * 
	 * @throws ObjectNotFoundException se não encontrar o objeto no banco de dados.
	 * */
	public Categoria find(Integer id) {
		
		/*
		 * findById() retorna um Objeto Optional.
		 * Um Objeto Optional funciona como um container na qual o Objeto Categoria pode ou
		 * não estar contido.
		 * 
		 * O uso de Optional evitará nullPointerException pois se o Objeto não for encontrado
		 * retornará um Optional vazio ao invés de um null.
		 * */
		Optional<Categoria> obj = repo.findById(id);
		
		/*
		 * Retorna o objeto Categoria presente dentro do "container" Optional.
		 * Caso não exista o objeto categoria, devido a negativa da busca anterior, lançará uma exceção.
		 * A exceção será lançada para a classe que chamou o método find().
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
		 * Salva o objeto e retorna o para o controller o próprio Objeto.
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
		 * Se não existir será lançada a exceção no método buscarPorId*/
		find(obj.getId());
		
		/*O método save é usado tanto para inserir quanto para atualizar.
		 * Isso é decidido de acordo com o ID. Se o id for nulo ele irá inserir o objeto
		 * se o id não for nulo, o objeto será atualizado*/
		return repo.save(obj);
	}

	public void delete(Integer id) {
		/*Verificando se o objeto a ser excluído realmente existe no banco de dados*/
		find(id);
		
		/*faz a exclusão pelo id. Pode lançar a exceção DataIntegrityViolationException
		 * caso o objeto seja referenciado em outras tabelas(integridade referencial) */
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e){
			
			/*lança a exceção personalizada que será capturada no pacote controller */
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}

	/**
	 * Método que busca todos os objetos no banco de dados.
	 * 
	 * @return uma Lista de objetos
	 * */
	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	/**
	 * Método que retorna um objeto Page. Padrão utilizado que faz a listagem de objetos por paginação.
	 * Uma página é uma sub-lista de uma lista de objetos. Em um banco de dados com muitos registros 
	 * é penoso utilizar o método findAll() que retorna TODOS os registros. Um objeto Page faz a busca
	 * por um número limitado de registro, não sobrecarregando o banco de dados com uma consulta 
	 * findAll() desnecessária.
	 * 
	 * @param page informa o número da página. A página inicial é a de número zero.
	 * @param linesPerPage Informa o número de registro máximo em cada página. Ou seja,
	 * quantas linhas por página. 
	 * @param orderBy O identificador do atributo pelo qual os registros serão ordenados. 
	 * @param direction Indica a direção da ordenação, podendo ser ascendente ou descendente(ASC ou DESC).
	 * 
	 * @return Page uma Page que é uma sub-lista contendo um número definido de registros
	 * */
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		/*
		 * O objeto do tipo PageRequest recebe o retorno da consulta. O método estático of retorna 
		 * um Objeto PageRequest de acordo com as propriedades definidas nos parâmetros.
		 * Esse objeto será usado para requisitar o Objeto Page no método findAll().
		 * */
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
}
