package com.filipe.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@GetMapping("")
	public String raiz() {
		return "Home";
	}
	
	@GetMapping("/olaMundo")
	public String helloWord() {
		return "Olá mundo!";
	}
}
