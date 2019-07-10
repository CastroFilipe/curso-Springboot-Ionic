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
	private ClienteRepository clienteRepository;
	
	/*
	 * Método que retorna um objeto. Caso não encontre lançará uma exceção personalizada 
	 * ObjectNotFoundException.
	 * Para mais detalhes ver a classe CategoriaService que possuí o mesmo método
	 * */
	public Cliente buscarPorId(Integer id) {
		Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		
		return clienteOptional.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não Encontrado! Id: " + id + " ,tipo:"+ Cliente.class.getName()));
	}
}
