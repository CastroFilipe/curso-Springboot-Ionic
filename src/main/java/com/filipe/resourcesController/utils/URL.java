package com.filipe.resourcesController.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Auxiliar que pega os parâmetros de uma URI referentes a uma categoria e os transforma em
 * números inteiros.
 * 
 *  Ex: URI: produtos/page?nome=computador&categoria=1,3,4
 *  
 *  Transforma a string "1,3,4" em inteiros.
 * */
public class URL {

	/**
	 * Método que faz o decode de uma String.
	 * 
	 * Por padrão Strings contendo espaços e caracteres especiais e que vem como parâmetro na URI 
	 * da requisição vem no formato encode. 
	 * Exemplo: encode :"TV%20LED" ao passar pelo método decodeParam : "TV LED"
	 * 
	 * @param s uma string encode como exemplo "TV%20LED"
	 * @return uma String decode UTF-8 como exemplo "TV LED"
	 * */
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}	

	/**
	 * Converte uma cadeia de caractere como "1,2,3" em uma Lista contendo os números inteiros 1,2 e 3
	 * */
	public static List<Integer> decodeIntList(String s) {
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
}
