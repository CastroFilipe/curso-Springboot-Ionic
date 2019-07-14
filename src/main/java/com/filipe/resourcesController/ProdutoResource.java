package com.filipe.resourcesController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.filipe.domain.Produto;
import com.filipe.dto.ProdutoDTO;
import com.filipe.resourcesController.utils.URL;
import com.filipe.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;

	/**
	 * Método que busca um objeto por Id
	 * 
	 * @param id do objeto a ser atualizado vindo através da URI
	 * @return um objeto ReponseEntity com o status Ok e com o objeto como conteúdo do corpo(body).
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
		/*
		 * Chama o método find(id). Esse método poderá lançar uma exceção 
		 * do tipo ObjectNotFoundException se o objeto não for encontrado no banco de dados.
		 * 
		 * */
		Produto produto = service.find(id);

		return ResponseEntity.ok().body(produto);
	}
	
	
	/**
	 * Método que faz a busca de um Produto usando o nome e uma lista de categorias como filtros.
	 * Exemplo: Utilize na URI /produtos/page/?nome=Smart&categorias=1,4 para buscar todos os 
	 * produtos que contenham a palavra Smart em seu nome e que pertençam as categorias 1 ou 4.
	 * 
	 * @param nome ou parte do nome de um produto. Esse parâmetro será usado para buscar 
	 * produtos com o mesmo nome nas consultas via Query JPQL.
	 * @param categorias parâmetro que representa os ids das categorias, separados por virgula.
	 *  Esse parâmetro será usado para buscar categorias nas consultas via Query JPQL.
	 * @param page informa o número da página. A página inicial é a de número zero.
	 * @param linesPerPage Informa o número de registro máximo em cada página. Ou seja,
	 * quantas linhas por página. 
	 * @param orderBy O identificador do atributo pelo qual os registros serão ordenados. 
	 * @param direction Indica a direção da ordenação, podendo ser ascendente ou descendente(ASC ou DESC).
	 * 
	 * @return Page<?> uma Page que é uma sub-lista contendo um número definido de registros
	 * */
	/*
	 * @RequestParam diferente do @PathVariable que pega o {id} da URI a anotação
	 * @RequestParam usará os parâmetros da URI como exemplo 
	 * "/page?page=1&linesPerPage=10&orderBy=nome" etc.
	 * 
	 * São utilizados valores padrões para cada parâmetro a sua passagem é opcional.
	 * */
	@GetMapping("/page")
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		/*Método que faz o Decode*/
		String nomeDecoded = URL.decodeParam(nome);
		
		/*Converte uma cadeia de caractere como "1,2,3" em uma Lista contendo os números inteiros 1,2 e 3*/
		List<Integer> ids = URL.decodeIntList(categorias);
		
		/*faz a busca através da Query pelo produto a partir do nome e de uma lista de categorias*/
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		
		/*Converte cada Produto em list em um ProdutoDTO */
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		
		/*retorna para a view a listaDTO contendo todos os produtos com determinado nome presente em
		 * determinadas categorias passadas nos parâmetros do método*/
		return ResponseEntity.ok().body(listDto);
	}
}
