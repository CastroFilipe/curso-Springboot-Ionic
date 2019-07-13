package com.filipe.resourcesController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filipe.domain.Cliente;
import com.filipe.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
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
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		
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
		Cliente cliente = service.find(id);
		
		return ResponseEntity.ok().body(cliente);
	}
}
