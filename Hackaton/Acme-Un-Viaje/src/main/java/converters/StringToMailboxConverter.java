
package converters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MailboxRepository;
import repositories.MailboxRepository;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import domain.Actor;
import domain.Mailbox;

@Component
@Transactional
public class StringToMailboxConverter implements Converter<String, Mailbox> {

	@Autowired
	MailboxRepository	mailboxRepository;

	@Autowired
	ActorService			actorService;


	@Override
	public Mailbox convert(final String text) {
		Mailbox result = null;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en StringToMailboxConverter IF: " + text);
				result = null;
			} else
				try {
					id = Integer.valueOf(text);
					result = this.mailboxRepository.findOne(id);
					System.out.println("Error en StringToMailboxConverter ELSE: " + result);
				} catch (final Throwable oops) {
					final UserAccount user = LoginService.getPrincipal();
					final Actor a = this.actorService.getActorByUserId(user.getId());

					System.out.println(a);

					final List<Mailbox> boxes = (List<Mailbox>) this.mailboxRepository.getBoxWithName(text);

					System.out.println(boxes);

					for (int i = 0; i < boxes.size(); i++)
						if (a.getMailboxes().contains(boxes.get(i)))
							result = boxes.get(i);

					System.out.println(boxes);

					System.out.println("EL SUPER NEW RESULT");
					System.out.println(result);

				}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToMailboxConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		System.out.println("result");

		System.out.println(result);
		return result;
	}
}