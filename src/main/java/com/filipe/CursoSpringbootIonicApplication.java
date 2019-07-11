package com.filipe;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.filipe.domain.Categoria;
import com.filipe.domain.Cidade;
import com.filipe.domain.Cliente;
import com.filipe.domain.Endereco;
import com.filipe.domain.Estado;
import com.filipe.domain.Pagamento;
import com.filipe.domain.PagamentoComBoleto;
import com.filipe.domain.PagamentoComCartao;
import com.filipe.domain.Pedido;
import com.filipe.domain.Produto;
import com.filipe.domain.enums.EstadoPagamento;
import com.filipe.domain.enums.TipoCliente;
import com.filipe.repositories.CategoriaRepository;
import com.filipe.repositories.CidadeRepository;
import com.filipe.repositories.ClienteRepository;
import com.filipe.repositories.EnderecoRepository;
import com.filipe.repositories.EstadoRepository;
import com.filipe.repositories.PagamentoRepository;
import com.filipe.repositories.PedidoRepository;
import com.filipe.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoSpringbootIonicApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringbootIonicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Categorias e produtos
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		//Estados e cidades
		Estado est1 = new Estado(null, "Minas gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));

		//Clientes e endereços
		Cliente cli1 = new Cliente(null,"Maria Silva", "maria@gmail.com","36378912377", TipoCliente.PESSOAFISICA);
		
		Endereco e1 = new Endereco(null,"Rua Flores","300","Apto 203","Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null,"Avenida Matos","105","Sala 800","Centro", "38777012", cli1, c2);
		
		cli1.getTelefones().addAll(Arrays.asList("27300000", "93000000"));
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		/*Pedidos*/
		
		//SimpleDateFormat para converter as Strings para um Date de acordo com a máscara
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

		/*Instanciando os pedidos ped1 e ped2. Como os pagamentos ainda não foram 
		 * instanciados setaremos como null em ambos os pedidos*/
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), null, cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), null, cli1, e2);
		
		/*Instanciado os pagamentos pagto1 e pagto2. Como o EstadoPagamento.PENDENTE em pagto2 
		 * a data de pagamento foi setada como null*/
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		
		Pagamento pagto2 = new PagamentoComBoleto(
				null, EstadoPagamento.PENDENTE, ped2, sdf2.parse("20/10/2017"), null);
		
		/*Agora que os Pagamentos foram instanciados podemos adicioná-los aos seus respectivos pedidos*/
		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);
		
		/*Por último adcionamos os pedidos a Lista de pedidos do Cliente.*/
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		/*Salvar primeiro os pedidos que são independentes dos pagamentos*/
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
	}

}
