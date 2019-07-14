package com.filipe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.filipe.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	/*O spring implementa o método de forma  pois o nome do método é padrão e identifica o seu 
	 * objetivo*/
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
}
