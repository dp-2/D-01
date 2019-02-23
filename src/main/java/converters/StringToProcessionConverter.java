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

import domain.Procession;
import repositories.ProcessionRepository;

@Component
@Transactional
public class StringToProcessionConverter implements Converter<String, Procession> {

	@Autowired
	ProcessionRepository processionRepository;


	@Override
	public Procession convert(final String text) {
		Procession result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.processionRepository.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
