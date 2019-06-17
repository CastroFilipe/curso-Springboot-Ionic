package com.filipe;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.filipe.domain.Categoria;
import com.filipe.services.CategoriaService;

@SpringBootApplication
public class CursoSpringbootIonicApplication implements CommandLineRunner {

	@Autowired
	private CategoriaService service;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringbootIonicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		service.salvarTodos(Arrays.asList(cat1,cat2));

	}

}
