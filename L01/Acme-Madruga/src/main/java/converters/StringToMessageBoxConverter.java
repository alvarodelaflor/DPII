
package converters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MessageBoxRepository;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import domain.Actor;
import domain.MessageBox;

@Component
@Transactional
public class StringToMessageBoxConverter implements Converter<String, MessageBox> {

	@Autowired
	MessageBoxRepository	messageBoxRepository;

	@Autowired
	ActorService			actorService;


	@Override
	public MessageBox convert(final String text) {
		MessageBox result = null;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en StringToMessageBoxConverter IF: " + text);
				result = null;
			} else
				try {
					id = Integer.valueOf(text);
					result = this.messageBoxRepository.findOne(id);
					System.out.println("Error en StringToMessageBoxConverter ELSE: " + result);
				} catch (final Throwable oops) {
					final UserAccount user = LoginService.getPrincipal();
					final Actor a = this.actorService.getActorByUserId(user.getId());

					System.out.println(a);

					final List<MessageBox> boxes = (List<MessageBox>) this.messageBoxRepository.getBoxWithName(text);

					System.out.println(boxes);

					for (int i = 0; i < boxes.size(); i++)
						if (a.getMessageBoxes().contains(boxes.get(i)))
							result = boxes.get(i);

					System.out.println(boxes);

					System.out.println("EL SUPER NEW RESULT");
					System.out.println(result);

				}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToMessageBoxConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		System.out.println("result");

		System.out.println(result);
		return result;
	}
}
