
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import services.ActorService;
import domain.Actor;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor> {

	@Autowired
	ActorService	actorService;


	@Override
	public Actor convert(final String text) {
		Actor result;
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
