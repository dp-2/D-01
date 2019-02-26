
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Brotherhood;
import services.BrotherhoodService;

@Component
@Transactional
public class StringToBrotherhoodConverter implements Converter<String, Brotherhood> {

	@Autowired
	BrotherhoodService actorService;


	@Override
	public Brotherhood convert(final String text) {
		Brotherhood result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.actorService.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
