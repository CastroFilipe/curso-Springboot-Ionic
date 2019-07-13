package com.filipe.resourcesController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.filipe.domain.Cliente;
import com.filipe.dto.ClienteDTO;
import com.filipe.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	/**
	 * Método que busca um objeto por Id
	 * 
	 * @param id do objeto a ser atualizado vindo através da URI
	 * 
	 * @return uma Resposta http com status ok e o objeto no body
	 * */
	/*
	 * @ResponseEntity<?> tipo do springframework que encapsula informações de uma 
	 * resposta HTTP para um serviço rest
	 * 
	 * @PathVariable, necessário para informar que o Integer id irá receber o {id} 
	 * que veio na uri
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		
		/*
		 * Chama o método find(id). Esse método poderá lançar uma exceção 
		 * do tipo ObjectNotFoundException se o objeto não for encontrado no banco de dados.
		 * 
		 * Quando a exceção for lançada o objeto handler do tipo ResourceExceptionHandler interceptará
		 * a exceção e fará o chamará o método adequado para trata-lá.
		 * 
		 * Objetos Handler utilizam a anotação @ControllerAdvice que, em resumo, interceptará
		 * a exceção lançada. Com isso o código de tratamento será colocado em outra classe,
		 * deixando o código mais organizado.
		 * 
		 * */
		Cliente cliente = service.find(id);
		
		return ResponseEntity.ok().body(cliente);
	}
	
	
	/**
	 * Método(PUT) para atualizar um objeto no banco de dados 
	 * 
	 * @param obj objeto a ser atualizado
	 * @param id do objeto a ser atualizado vindo através da URI
	 * 
	 * @return uma resposta sem conteúdo, com status 204 No Content
	 * */
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDto);
		
		/*apenas uma garantia de que o objeto a ser editado será o mesmo do 
		 * id vindo nos parâmetros*/
		obj.setId(id);
		
		obj = service.update(obj);
		
		/*Uma resposta sem conteúdo*/
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Método(DELETE) para remover um objeto do banco de dados
	 * 
	 * @param id o id do objeto a ser removido
	 * 
	 * @return uma reposta com corpo(body) vazio.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		/*
		 * Método delete(id) irá deletar o objeto se este não possuir uma referência em outras classes.
		 * Caso possua referências o método delete lança uma exceção do tipo DataIntegrityException
		 * do pacote service.exceptions e que será interceptada pelo ResourceExceptionHandler do
		 * pacote controller.exceptions
		 * */
		
		service.delete(id);
		
		/*Se excluído com sucesso, retorna uma resposta sem conteúdo (corpo vazio)*/
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Método que busca todas os Objetos
	 * 
	 * @return uma Resposta http com status ok(status 200) e a lista de objetos no corpo da resposta.
	 * */
	/*
	 * @ResponseEntity<?> tipo do springframework que encapsula informações de uma 
	 * resposta HTTP para um serviço rest
	 * */
	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> findAll() {
		
		/*Faz a busca por todas as Clientes */
		List<Cliente> list = service.findAll();
		
		/*O laço for percorre list e cria um objeto ClienteDTO para cada Cliente presente 
		 * em list.
		 * 
		 * */
		List<ClienteDTO> listaDTO = new ArrayList<>();
		for(Cliente cliente : list) {
			listaDTO.add(new ClienteDTO(cliente));
		}
		
		/*cria um objeto ReponseEntity com o status Ok e com uma lista no conteúdo do corpo*/
		return ResponseEntity.ok().body(listaDTO);
	}
	
	/**
	 * Método que retorna um objeto Page. Padrão utilizado que faz a listagem de objetos por paginação.
	 * Uma página é uma sub-lista de uma lista de objetos. Em um banco de dados com muitos registros 
	 * é penoso utilizar o método findAll() que retorna TODOS os registros. Um objeto Page faz a busca
	 * por um número limitado de registro, não sobrecarregando o banco de dados com uma consulta 
	 * findAll() desnecessária.
	 * Os parâmetros tem um valor padrão definido, por isso sua passagem é opcional.
	 * 
	 * @param page informa o número da página. A página inicial é a de número zero.
	 * @param linesPerPage Informa o número de registro máximo em cada página. Ou seja,
	 * quantas linhas por página. 
	 * @param orderBy O identificador do atributo pelo qual os registros serão ordenados. 
	 * @param direction Indica a direção da ordenação, podendo ser ascendente ou descendente(ASC ou DESC).
	 * 
	 * @return Page<?> uma Page que é uma sub-lista contendo um número definido de registros
	 * */
	/*
	 * @RequestParam diferente do @PathVariable que pega o {id} da URI a anotação
	 * @RequestParam usará os parâmetros da URI como exemplo 
	 * "/page?page=1&linesPerPage=10&orderBy=nome" etc.
	 * 
	 * São utilizados valores padrões para cada parâmetro a sua passagem é opcional.
	 * */
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		/*Chama o método findPage() que retorna uma Page<?> de acordo com os parâmetros*/
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		
		/*Converte a Page<?> em uma Page<?DTO> enviando apenas as informações necessárias
		 * que queremos exibir de acordo com o DTO*/
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}
}
