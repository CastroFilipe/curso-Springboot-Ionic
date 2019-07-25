package com.filipe.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.filipe.domain.Cliente;
import com.filipe.domain.enums.TipoCliente;
import com.filipe.dto.ClienteNewDTO;
import com.filipe.repositories.ClienteRepository;
import com.filipe.resources.exceptions.FieldMessage;
import com.filipe.services.validation.utils.BR;

/**
 * Classe que representa um Validator personalizado. Será usado através da anotação @ClienteInsert
 * A validação será feita apenas em classes ClienteNewDTO pois ela é um dos parâmetros da classe. 
 * 
 * */
/*
 * Classe apenas de curiosidade para testar as diversas possibilidades de implementação de um
 * validator personalizado
 * 
 * ConstraintValidator indica a anotação que será usada e a classe que aceitara a anotação.
 * */

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	//método que valida os campos.
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		//se for uma pessoa física fará uma validação de CPF
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		//se for uma pessoa Jurídica fará uma validação de CNPJ
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		//Teste para impedir que um email repetido seja inserido no banco de dados.
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		//Se a lista estiver vazia retorna true: os testes foram bem sucedidos e a lista não contém nenhum erro. 
		return list.isEmpty();
	}
}
