
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Url;

@Component
@Transactional
public class UrlToStringConverter implements Converter<Url, String> {

	@Override
	public String convert(final Url url) {
		return url.getUrl();
	}

}
