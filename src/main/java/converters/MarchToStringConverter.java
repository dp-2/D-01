
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.March;

@Component
@Transactional
public class MarchToStringConverter implements Converter<March, String> {

	@Override
	public String convert(final March march) {
		String result;

		if (march == null)
			result = null;
		else
			result = String.valueOf(march.getId());

		return result;
	}

}
