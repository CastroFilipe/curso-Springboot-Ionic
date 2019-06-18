package com.filipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipe.domain.Categoria;
import com.filipe.repositories.CategoriaRepository;
import com.filipe.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;

	//metodo que busca uma Categoria por ID, caso não encontre Lança uma exceção personalizada
	public Categoria buscarPorId(Integer id) {
		//findById retorna um Objeto Optional: serve como um container e evita nullPointerException
		Optional<Categoria> obj = repo.findById(id);
		
		//a exceção será lançada para a classe que chamou o metodo buscarPorId().
		return obj.orElseThrow(()->new ObjectNotFoundException(
				"Objeto não Encontrado! Id: " + id + " ,tipo:"+ Categoria.class.getName()));
	}
}
