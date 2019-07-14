package com.filipe.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.filipe.domain.enums.TipoCliente;
import com.filipe.dto.ClienteNewDTO;
import com.filipe.resourcesController.exceptions.FieldMessage;
import com.filipe.services.validation.utils.BR;

/**
 * Classe que representa um Validator personalizado. Será usado através da anotação @ClienteInsert
 * A validação será feita apenas em classes ClienteNewDTO pois ela é um dos parâmetros da classe. 
 * 
 * */
/*
 * Classe apenas de curiosidade para testar as diversas possibilidades de implementação de um
 * validator personalizado
 * */

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
