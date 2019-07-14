package com.filipe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipe.domain.PagamentoComBoleto;
import com.filipe.domain.PagamentoComCartao;
/**
 * É necessário registrar as subclasses PagamentoComBoleto e PagamentoComCartao.
 * A classe JacksonConfig registra ambas as subclasses pois elas foram anotadas 
 * com @JsonTypeName e a superclasse Pagamento foi anotada com @JsonTypeInfo
 * 
 * Esse Código é padrão de exigência da biblioteca Jackson.
 * O que mudará de um projeto para outro são as subclasses que se quer cadastrar.
 * */
@Configuration
public class JacksonConfig {
	// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class);//ao mudar de projeto, mudar as subclasses aqui.
				objectMapper.registerSubtypes(PagamentoComBoleto.class);//ao mudar de projeto, mudar as subclasses aqui.
				super.configure(objectMapper);
			}
		};
		return builder;
	}
}
