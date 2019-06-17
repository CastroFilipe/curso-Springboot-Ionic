package com.filipe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipe.domain.Categoria;
import com.filipe.repositories.InterfaceCategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private InterfaceCategoriaRepository repo;

	//metodo que busca uma Categoria por ID
	public Categoria buscarPorId(Integer id) {
		//Objeto Optional serve como um container. Evita nullPointerException
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null); //retorna null caso o obejeto não for encontrado.
	}
	
//	public void salvarTodos(List<Categoria> categorias) {
//		repo.saveAll(categorias);
//	}
}
