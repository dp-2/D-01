
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.MarchService;
import domain.March;

@Component
@Transactional
public class StringToMarchConverter implements Converter<String, March> {

	@Autowired
	MarchService	marchService;


	@Override
	public March convert(final String text) {
		March result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.marchService.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
