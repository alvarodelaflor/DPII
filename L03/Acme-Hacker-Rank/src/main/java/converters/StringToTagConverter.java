
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TagRepository;
import services.TagService;
import domain.Tag;

@Component
public class StringToTagConverter implements Converter<String, Tag> {

	@Autowired
	TagRepository	tagRepository;

	@Autowired
	TagService		tagService;


	@Override
	public Tag convert(final String text) {
		Tag result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else
				result = this.tagService.create();
			result.setTag(text);
			result.setMessageId(0);
			result.setActorId(0);
		} catch (final Throwable oops) {
			System.out.println("Error en StringToTagConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
