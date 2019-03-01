
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.DFloat;

@Component
@Transactional
public class DFloatToStringConverter implements Converter<DFloat, String> {

	@Override
	public String convert(final DFloat dfloat) {
		String result;

		if (dfloat == null)
			result = null;
		else
			result = String.valueOf(dfloat.getId());

		return result;
	}

}
