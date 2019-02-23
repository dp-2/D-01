/*
 * CurriculaToStringConverter.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Message;

@Component
@Transactional
public class MessageToStringConverter implements Converter<Message, String> {

	@Override
	public String convert(final Message message) {
		String result;

		if (message == null)
			result = null;
		else
			result = String.valueOf(message.getId());

		return result;
	}

}
