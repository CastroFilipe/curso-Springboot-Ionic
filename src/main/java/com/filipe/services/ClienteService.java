package com.filipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filipe.domain.Cliente;
import com.filipe.repositories.ClienteRepository;
import com.filipe.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	/**
	 * Método que busca um objeto por ID, caso não encontre Lança uma exceção 
	 * personalizada do tipo ObjectNotFoundException. Para mais informações consultar o mesmo
	 * método na classe @Categoria
	 * 
	 * @param id o Id do objeto buscado.
	 * 
	 * @throws ObjectNotFoundException se não encontrar o objeto no banco de dados.
	 * */
	public Cliente find(Integer id) {
		Optional<Cliente> clienteOptional = repo.findById(id);
		
		return clienteOptional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não Encontrado! Id: " + id + " ,tipo:"+ Cliente.class.getName()));
	}
}
