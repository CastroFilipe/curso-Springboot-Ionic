package com.filipe.resourcesController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.filipe.domain.Categoria;
import com.filipe.dto.CategoriaDTO;
import com.filipe.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	/**
	 * Método que busca um objeto por Id
	 * 
	 * @param id do objeto a ser atualizado vindo através da URI
	 * 
	 * @return uma Resposta http com status ok e o objeto no body
	 * */
	/*
	 * @ResponseEntity<?> tipo do springframework que encapsula informações de uma 
	 * resposta HTTP para um serviço rest
	 * 
	 * @PathVariable, necessário para informar que o Integer id irá receber o {id} 
	 * que veio na uri
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		/*
		 * Chama o método find(id). Esse método poderá lançar uma exceção 
		 * do tipo ObjectNotFoundException se o objeto não for encontrado no banco de dados.
		 * 
		 * Quando a exceção for lançada o objeto handler do tipo ResourceExceptionHandler interceptará
		 * a exceção e fará o chamará o método adequado para trata-lá.
		 * 
		 * Objetos Handler utilizam a anotação @ControllerAdvice que, em resumo, interceptará
		 * a exceção lançada. Com isso o código de tratamento será colocado em outra classe,
		 * deixando o código mais organizado.
		 * 
		 * */
		Categoria categoria = service.find(id);

		//cria um objeto ReponseEntity com o status Ok e com o objeto como conteúdo do corpo.
		return ResponseEntity.ok().body(categoria);
	}
	
	/**
	 * Método para inserir uma nova categoria(POST).  
 	 * 
 	 * @param obj objeto a ser inserido
	 * 
	 * @return uma resposta Http com status 201, sem corpo e com uma URI nos headers que 
	 * referencia o novo objeto criado.
	 * */
	/*
	 * @RequestBody : O objeto Categoria será construído a partir do objeto Json enviado
	 * no corpo da requisição
	 * 
	 * ResponseEntity<void> Quando inserir uma categoria com sucesso indica que 
	 * será retornado uma resposta Http sem corpo.
	 * */
	@PostMapping()
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		
		/*
		 * Método que insere um novo objeto e retorna o objeto inserido já com o novo id
		 * */
		obj = service.insert(obj);
		
		/*
		 * Por padrão, o status http 201 CREATED deve retornar o objeto criado e a URI do
		 * novo objeto criado. A linha abaixo criará uma URI para referenciar o novo objeto.
		 * Exemplo : categorias/{id} 
		 * */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		
		/*Retorna uma resposta HTTP sem corpo com um status e o uri no Headers que referência o novo obj 
		 * Ex: categorias/{id}*/
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Método(PUT) para atualizar um objeto no banco de dados 
	 * 
	 * @param obj objeto a ser atualizado
	 * @param id do objeto a ser atualizado vindo através da URI
	 * 
	 * @return uma resposta sem conteúdo, com status 204 No Content
	 * */
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		/*apenas uma garantia de que o objeto a ser editado será o mesmo do 
		 * id vindo nos parametros*/
		obj.setId(id);
		
		obj = service.update(obj);
		
		/*Uma resposta sem conteúdo*/
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Método(DELETE) para remover um objeto do banco de dados
	 * 
	 * @param id o id do objeto a ser removido
	 * 
	 * @return uma reposta com corpo(body) vazio.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		/*
		 * Método delete(id) irá deletar o objeto se este não possuir uma referência em outras classes.
		 * Caso possua referências o método delete lança uma exceção do tipo DataIntegrityException
		 * do pacote service.exceptions e que será interceptada pelo ResourceExceptionHandler do
		 * pacote controller.exceptions
		 * */
		
		service.delete(id);
		
		/*Se excluído com sucesso, retorna uma resposta sem conteúdo (corpo vazio)*/
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Método que busca todas os Objetos
	 * 
	 * @return uma Resposta http com status ok(status 200) e a lista de objetos no corpo da resposta.
	 * */
	/*
	 * @ResponseEntity<?> tipo do springframework que encapsula informações de uma 
	 * resposta HTTP para um serviço rest
	 * */
	@GetMapping()
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		
		/*Faz a busca por todas as Categorias */
		List<Categoria> list = service.findAll();
		
		/*O laço for percorre list e cria um objeto CategoriaDTO para cada Categoria presente 
		 * em list. Esse processo é necessário pois queremos exibir na view apenas o id e o nome
		 * das categorias quando o endpoint "/categorias" for chamado.
		 * 
		 * */
		List<CategoriaDTO> listaDTO = new ArrayList<>();
		for(Categoria categoria : list) {
			listaDTO.add(new CategoriaDTO(categoria));
		}
		
		/*cria um objeto ReponseEntity com o status Ok e com uma lista no conteúdo do corpo*/
		return ResponseEntity.ok().body(listaDTO);
	}
	
}
