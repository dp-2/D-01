/*
 * StringToCurriculaConverter.java
 * 
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BoxRepository;
import domain.Box;

@Component
@Transactional
public class StringToBoxConverter implements Converter<String, Box> {

	@Autowired
	BoxRepository	boxRepository;


	@Override
	public Box convert(final String text) {
		Box result;
		int id;

		try {
			if (text == "")
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.boxRepository.findOne(id);
			}
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
