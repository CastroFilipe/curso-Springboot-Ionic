package com.filipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipe.domain.Categoria;
import com.filipe.repositories.InterfaceCategoriaRepository;
import com.filipe.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private InterfaceCategoriaRepository repo;

	//metodo que busca uma Categoria por ID
	public Categoria buscarPorId(Integer id) {
		//Objeto Optional serve como um container. Evita nullPointerException
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()->new ObjectNotFoundException(
				"Objeto não Encontrado! Id: " + id + " ,tipo:"+ Categoria.class.getName(), 
				new Throwable().getCause()));
	}
	
//	public void salvarTodos(List<Categoria> categorias) {
//		repo.saveAll(categorias);
//	}
}
