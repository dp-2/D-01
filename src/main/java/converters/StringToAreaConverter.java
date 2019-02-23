
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Area;
import services.AreaService;

@Component
@Transactional
public class StringToAreaConverter implements Converter<String, Area> {

	@Autowired
	AreaService areaService;


	@Override
	public Area convert(final String text) {
		Area result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.areaService.findOne(id);
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
