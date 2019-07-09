package com.filipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filipe.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	
}
