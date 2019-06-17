package com.filipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filipe.domain.Produto;

@Repository
public interface InterfaceProdudoRepository extends JpaRepository<Produto, Integer> {
	
	
}
