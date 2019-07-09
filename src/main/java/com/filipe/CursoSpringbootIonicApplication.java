package com.filipe;

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
import com.filipe.domain.Produto;
import com.filipe.domain.enums.TipoCliente;
import com.filipe.repositories.CategoriaRepository;
import com.filipe.repositories.CidadeRepository;
import com.filipe.repositories.ClienteRepository;
import com.filipe.repositories.EnderecoRepository;
import com.filipe.repositories.EstadoRepository;
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
		
		System.out.println("endereços "+cli1.getEnderecos());
		
		
	}

}
