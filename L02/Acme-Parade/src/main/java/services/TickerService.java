
package services;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ParadeRepository;

@Service
public class TickerService {

	@Autowired
	public ParadeRepository	paradeRepository;


	public String randomTicker() {
		String ticker = "";
		Boolean res = true;
		while (res) {
			ticker = "";
			final String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 6; i++) {
				final int randomInt = new SecureRandom().nextInt(characterSet.length());
				sb.append(characterSet.substring(randomInt, randomInt + 1));
			}
			final Date date = DateTime.now().toDate();
			final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
			final String dateConvert = sdf.format(date);
			ticker = dateConvert.replaceAll("-", "") + "-" + sb.toString();
			if (this.paradeRepository.findParadesByTicker(ticker).isEmpty())
				res = false;
		}
		return ticker;
	}
}
