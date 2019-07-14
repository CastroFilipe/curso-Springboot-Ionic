package com.filipe.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.filipe.domain.Categoria;
import com.filipe.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
	/**
	 * Método que faz a busca de um produto a partir do nome e de uma lista de categorias a qual o produto
	 * possa estar contido.
	 * 
	 * */
	/* 
	 * O framework Spring facilita a criação de métodos com consultas JPQL. Por isso não é necessário
	 * a implementação desse método numa classe que implemente a interface ProdutoRepository.
	 * De forma automática o Spring criará o método que faz a consulta JPQL definida em @Query
	 * 
	 * @Query define a consulta JPQL que será feita no banco de dados.
	 * @Param indica quais parâmetros do método são as variáveis correspondentes na Query.
	 */ 
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(
			@Param("nome") String nome, 
			@Param("categorias") List<Categoria> categorias, 
			Pageable pageRequest);
}
