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

import repositories.MemberRepository;
import domain.Member;

@Component
@Transactional
public class StringToEnrollConverter implements Converter<String, Member> {

	@Autowired
	MemberRepository	memberRepository;


	@Override
	public Member convert(final String text) {
		Member result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.memberRepository.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
