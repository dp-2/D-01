
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Enroll;

@Component
@Transactional
public class EnrollToStringConverter implements Converter<Enroll, String> {

	@Override
	public String convert(final Enroll enroll) {
		String result;

		if (enroll == null)
			result = null;
		else
			result = String.valueOf(enroll.getId());

		return result;
	}

}
