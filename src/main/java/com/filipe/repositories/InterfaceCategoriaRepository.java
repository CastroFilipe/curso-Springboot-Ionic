package com.filipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filipe.domain.Categoria;

@Repository
public interface InterfaceCategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	
}
