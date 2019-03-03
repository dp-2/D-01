
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Url;

@Component
@Transactional
public class StringToUrlConverter implements Converter<String, Url> {

	@Override
	public Url convert(final String arg0) {
		final Url res = new Url();
		res.setUrl(arg0);
		return res;
	}

}
