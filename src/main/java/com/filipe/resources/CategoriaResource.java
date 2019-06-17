package com.filipe.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filipe.domain.Categoria;
import com.filipe.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	//ResponseEntity<?> tipo do springframework que encapsula informações de uma resposta HTTP para um serviço rest
	//@PathVariable, necessário para informar que o Integer id irá receber o {id} da url
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria categoria = service.buscarPorId(id);

		//cria um objeto ReponseEntity com o status Ok e com uma categoria como conteúdo do corpo.
		return ResponseEntity.ok().body(categoria);
	}
	
}
