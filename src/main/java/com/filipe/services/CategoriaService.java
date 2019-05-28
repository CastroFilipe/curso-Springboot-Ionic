package com.filipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipe.domain.Categoria;
import com.filipe.repositories.InterfaceCategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private InterfaceCategoriaRepository repository;

	public Categoria buscarPorId(Long id) {	
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElse(null);
	}
}
