package com.filipe.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Classe auxiliar que representa uma anotação personalizada @ClienteInsert.
 * A anotação deve ser usada em conjunto com um Validator. 
 * Nesse caso usamos o validator personalizado ClienteInsertValidator
 * 
 * */
/*
 * Classe apenas de curiosidade para testar as diversas possibilidades de implementação de um
 * validator personalizado
 * */

@Constraint(validatedBy = ClienteInsertValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteInsert {
	String message() default "Erro de validação";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}