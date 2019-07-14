package com.filipe.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.filipe.domain.ItemPedido;
import com.filipe.domain.PagamentoComBoleto;
import com.filipe.domain.Pedido;
import com.filipe.domain.enums.EstadoPagamento;
import com.filipe.repositories.ItemPedidoRepository;
import com.filipe.repositories.PagamentoRepository;
import com.filipe.repositories.PedidoRepository;
import com.filipe.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	/**
	 * Método que busca um objeto por ID, caso não encontre Lança uma exceção 
	 * personalizada do tipo ObjectNotFoundException. Para mais informações consultar o mesmo
	 * método na classe @Categoria
	 * 
	 * @param id o Id do objeto buscado.
	 * 
	 * @throws ObjectNotFoundException se não encontrar o objeto no banco de dados.
	 * */
	public Pedido find(Integer id) {
		
		/*
		 * findById() retorna um Objeto Optional.
		 * Um Objeto Optional funciona como um container na qual o Objeto Pedido pode ou
		 * não estar contido.
		 * 
		 * O uso de Optional evitará nullPointerException pois se o Objeto não for encontrado
		 * retornará um Optional vazio ao invés de um null.
		 * */
		Optional<Pedido> obj = repo.findById(id);
		
		/*
		 * Retorna o objeto Pedido presente dentro do "container" Optional.
		 * Caso não exista o objeto Pedido, devido a negativa da busca anterior, lançará uma exceção.
		 * A exceção será lançada para a classe que chamou o metodo find()
		 * 
		 * ObjectNotFoundException é uma exceção personalizada criada no pacote services.exceptions
		 * */

		return obj.orElseThrow(()->new ObjectNotFoundException(
				"Objeto não Encontrado! Id: " + id + " ,tipo:"+ Pedido.class.getName()));
	}

	/**
	 * Método para inserir um novo pedido
	 * 
	 * @param obj contendo as informações do Pedido vindos da view
	 * */
	@Transactional
	public Pedido insert(Pedido obj) {
		/*MODIFICAÇÕES NO PEDIDO:*/
		/* id = null para garantir que está salvando um novo pedido e não editando*/
		obj.setId(null);
		
		/*adiciona a data do pedido*/
		obj.setInstante(new Date());
		
		/*MODIFICAÇÕES NO PAGAMENTO DO PEDIDO*/
		/*Define o atual estado do pagamento*/
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		
		/*Faz o mapeamento inverso: seta o atributo pedido presente na classe Pagamento
		 * Assim o objeto do tipo Pagamento sabe a qual pedido pertence*/
		obj.getPagamento().setPedido(obj);
		
		/*Se o Pagamento presente no Pedido obj for do tipo PagamentoComBoleto:*/
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();//faz o casting
			
			/*Como não está sendo usado um web service que gere um boleto, o método 
			 * preencherPagamentoComBoleto irá gerar uma data de vencimento de uma 
			 * semana após a data do registro do pedido*/
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		/*Salva o pedido no banco*/
		obj = repo.save(obj);
		
		/*Salva o pagamento no banco*/
		pagamentoRepository.save(obj.getPagamento());
		
		/*Percorre todos os itens de pedido presentes no pedido obj*/
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			
			/*o preço será obtido via consulta do produto no banco de dados*/
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
