package com.filipe.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.filipe.domain.PagamentoComBoleto;
/**
 * Uma classe que simula a geração de um boleto. Possui apenas um método que adiciona uma
 * data de vencimento a um PagamentoComBoleto.
 * */
@Service
public class BoletoService {
	
	/**Adiciona uma data de vencimento a um PagamentoComBoleto. A data será de uma semana 
	 * após o instante do pedido*/
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
