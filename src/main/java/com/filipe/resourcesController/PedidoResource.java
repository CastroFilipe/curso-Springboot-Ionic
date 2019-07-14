package com.filipe.resourcesController;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.filipe.domain.Pedido;
import com.filipe.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;

	/**
	 * Método que busca um objeto por Id
	 * 
	 * @param id do objeto a ser atualizado vindo através da URI
	 * 
	 * @return uma Resposta http com status ok e o objeto no corpo(body)
	 * */
	/*
	 * @ResponseEntity<?> tipo do springframework que encapsula informações de uma 
	 * resposta HTTP para um serviço rest
	 * 
	 * @PathVariable, necessário para informar que o Integer id irá receber o {id} 
	 * que veio na uri
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
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
		Pedido pedido = service.find(id);

		//cria um objeto ReponseEntity com o status Ok e com o objeto como conteúdo do corpo.
		return ResponseEntity.ok().body(pedido);
	}
	
	/**Método que insere um pedido*/
	@PostMapping()
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){
		/*
		 * Método que insere um novo objeto e retorna o objeto inserido já com o novo id
		 * */
		obj = service.insert(obj);
		
		/*
		 * Por padrão, o status http 201 CREATED deve retornar o objeto criado e a URI do
		 * novo objeto criado. A linha abaixo criará uma URI para referenciar o novo objeto.
		 * Exemplo : pedidos/{id} 
		 * */
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		
		/*Retorna uma resposta HTTP sem corpo com um status e o uri no Headers que referência o novo obj 
		 * Ex: pedidos/{id}*/
		return ResponseEntity.created(uri).build();
	}
}
