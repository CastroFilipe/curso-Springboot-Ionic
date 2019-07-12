package com.filipe.resourcesController;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.filipe.domain.Categoria;
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
	 * que veio na url
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		/*
		 * Chama o método buscarPorId(). Esse método poderá lançar uma exceção 
		 * (ObjectNotFoundException) se o objeto não for encontrado.
		 * 
		 * Se a exceção for lançada: Utilizar um tryCatch para tratar ou utilizar um Objeto 
		 * do tipo Handler.
		 * Objetos Handler utilizam a anotação @ControllerAdvice que, em resumo, interceptará
		 * a exceção lançada. Com isso o códio de tratamento será colocado em outra classe,
		 * deixando o código mais organizado.
		 * 
		 * */
		Categoria categoria = service.find(id);

		//cria um objeto ReponseEntity com o status Ok e com uma categoria como conteúdo do corpo.
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
	
}
