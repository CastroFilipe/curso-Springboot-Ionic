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
	private ClienteService clienteService;
	
	/*
	 * Recebe um Id, faz a busca e retorna um cliente no corpo da resposta.
	 * Para mais informações ver a classe CategoriaResource que possui o mesmo método 
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		Cliente cliente = clienteService.buscarPorId(id);
		
		return ResponseEntity.ok().body(cliente);
	}
}
