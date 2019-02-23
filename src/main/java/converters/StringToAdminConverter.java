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

import domain.Administrator;
import repositories.AdministratorRepository;

@Component
@Transactional
public class StringToAdminConverter implements Converter<String, Administrator> {

	@Autowired
	AdministratorRepository administratorRepository;


	@Override
	public Administrator convert(final String text) {
		Administrator result;
		int id;

		try {
			if (text == "")
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.administratorRepository.findOne(id);
			}
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
