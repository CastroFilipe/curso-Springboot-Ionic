package com.filipe.resourcesController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filipe.domain.Pedido;
import com.filipe.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	/**
	 * @ResponseEntity<?> tipo do springframework que encapsula informações de uma 
	 * resposta HTTP para um serviço rest
	 * 
	 * @PathVariable, necessário para informar que o Integer id irá receber o {id} 
	 * que veio na url
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		/**
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
		Pedido pedido = service.buscarPorId(id);

		//cria um objeto ReponseEntity com o status Ok e com uma categoria como conteúdo do corpo.
		return ResponseEntity.ok().body(pedido);
	}
}
