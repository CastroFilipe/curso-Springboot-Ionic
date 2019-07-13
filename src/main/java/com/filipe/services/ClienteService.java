package com.filipe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.filipe.domain.Cliente;
import com.filipe.dto.ClienteDTO;
import com.filipe.repositories.ClienteRepository;
import com.filipe.services.exceptions.DataIntegrityException;
import com.filipe.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	/**
	 * Método que busca um objeto por ID, caso não encontre Lança uma exceção 
	 * personalizada do tipo ObjectNotFoundException. Para mais informações consultar o mesmo
	 * método na classe @Cliente
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
	
	/**
	 * Método para atualizar um objeto no banco de dados.
	 * 
	 * @param obj o objeto a ser atualizado
	 * */
	public Cliente update(Cliente obj) {
		/*Antes de atualizar fazer uma busca no banco para garantir que o objeto exista no banco
		 * Se não existir será lançada a exceção no método buscarPorId*/
		Cliente newObj = find(obj.getId());
		
		/*new Obj é o objeto atual que está salvo no banco. UpdateData é o método para 
		 * atualizar os atributos de newObj a partir do obj que veio da view. Após isso o newObj
		 * será salvo com os novos atributos atualizados*/
		updateData(newObj, obj);
		
		/*O método save é usado tanto para inserir quanto para atualizar.
		 * Isso é decidido de acordo com o ID. Se o id for nulo ele irá inserir o objeto
		 * se o id não for nulo, o objeto será atualizado*/
		return repo.save(newObj);
	}

	/**
	 * Método auxiliar que atualiza um objeto do banco de dados
	 * */
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		/*Verificando se o objeto a ser excluído realmente existe no banco de dados*/
		find(id);
		
		/*faz a exclusão pelo id. Pode lançar a exceção DataIntegrityViolationException
		 * caso o objeto seja referenciado em outras tabelas(integridade referencial) */
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e){
			
			/*lança a exceção personalizada que será capturada no pacote controller */
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	/**
	 * Método que busca todos os objetos no banco de dados.
	 * 
	 * @return uma Lista de objetos
	 * */
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	/**
	 * Método que retorna um objeto Page. Padrão utilizado que faz a listagem de objetos por paginação.
	 * Uma página é uma sub-lista de uma lista de objetos. Em um banco de dados com muitos registros 
	 * é penoso utilizar o método findAll() que retorna TODOS os registros. Um objeto Page faz a busca
	 * por um número limitado de registro, não sobrecarregando o banco de dados com uma consulta 
	 * findAll() desnecessária.
	 * 
	 * @param page informa o número da página. A página inicial é a de número zero.
	 * @param linesPerPage Informa o número de registro máximo em cada página. Ou seja,
	 * quantas linhas por página. 
	 * @param orderBy O identificador do atributo pelo qual os registros serão ordenados. 
	 * @param direction Indica a direção da ordenação, podendo ser ascendente ou descendente(ASC ou DESC).
	 * 
	 * @return Page uma Page que é uma sub-lista contendo um número definido de registros
	 * */
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		/*
		 * O objeto do tipo PageRequest recebe o retorno da consulta. O método estático of retorna 
		 * um Objeto PageRequest de acordo com as propriedades definidas nos parâmetros.
		 * Esse objeto será usado para requisitar o Objeto Page no método findAll().
		 * */
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	/**
	 * Método auxiliar que converte um ObjetoDTO em um Objeto.
	 * 
	 * @param objDto o objeto DTO para ser convertido.
	 * 
	 * @return um objeto convertido.
	 * */
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(), null, null);
	}
}
