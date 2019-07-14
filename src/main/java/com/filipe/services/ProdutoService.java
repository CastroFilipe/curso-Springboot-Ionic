package com.filipe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.filipe.domain.Categoria;
import com.filipe.domain.Produto;
import com.filipe.repositories.CategoriaRepository;
import com.filipe.repositories.ProdutoRepository;
import com.filipe.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository;

	/**Método que retorna um produto ou a exceção ObjectNotFoundException caso não o encontre*/
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	/**
	 * Método que faz a busca de um Produto usando o nome e uma lista de categorias como filtros.
	 * 
	 * @param nome o nome do produto que se quer encontrar ou parte do nome
	 * @param ids uma lista de ids das categorias onde o produto deve ser procurado.
	 * 
	 * @param page informa o número da página. A página inicial é a de número zero.
	 * @param linesPerPage Informa o número de registro máximo em cada página. Ou seja,
	 * quantas linhas por página. 
	 * @param orderBy O identificador do atributo pelo qual os registros serão ordenados. 
	 * @param direction Indica a direção da ordenação, podendo ser ascendente ou descendente(ASC ou DESC).
	 * 
	 * @return Page uma Page que é uma sub-lista contendo um número definido de registros
	 * */
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		/*cria um objeto PageRequest com os parâmetros para a requisição*/
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		/*Faz a busca pelas categorias informadas*/
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
	}
}
