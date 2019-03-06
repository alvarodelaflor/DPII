
package services;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Transactional
public class WelcomeService {

	String					e			= "Welcome to Acme Madrugá, the site to organise your processions.";

	String					s			= "ˇBienvenidos a Acme Madrugá! Tu sitio para organizar procesiones.";

	String					system		= "Acme Madrugá";

	String					phone		= "34";

	String					country		= "Espańa/Spain";

	@URL
	String					logo		= "https://tinyurl.com/acme-madruga";

	public HashSet<String>	spamWords	= new HashSet<>();


	//Carmen: Método para ańadir spam words (adm)
	public HashSet<String> newSpamWords(final String newWord) {
		this.spamWords.add(newWord);
		return this.spamWords;
	}

	public HashSet<String> getSpamWords() {
		return this.spamWords;
	}

	public HashSet<String> deleteSpamWords(final String newWord) {
		this.spamWords.remove(newWord);
		Assert.isTrue(this.getSpamWords().contains(newWord), "noSpamWord.error");
		return this.spamWords;
	}

	//Carmen: Método para mostrar las spam words
	public HashSet<String> listSpamWords() {

		this.spamWords.add("sex");
		this.spamWords.add("viagra");
		this.spamWords.add("cialis");
		this.spamWords.add("one millon");
		this.spamWords.add("you've been selected");
		this.spamWords.add("Nigeria");
		this.spamWords.add("sexo");
		this.spamWords.add("un millón");
		this.spamWords.add("ha sido seleccionado");

		return this.spamWords;
	}

	public String getLogo() {
		return this.logo;
	}

	public String newLogo(final String newLogo) {
		//		Assert.isTrue(!this.checkUrl(newLogo), "logo.bad");
		this.logo = newLogo;
		return this.logo;
	}

	private Boolean checkUrl(final String url) {
		Boolean res = true;
		final String[] elementos = url.split("://");
		final String elemento1 = elementos[0];
		final String elemento2 = elementos[1];
		if (elemento1 == "https" || elemento1 == "http")
			res = false;
		return res;
	}

	public String newE(final String newE) {
		this.e = newE;
		return this.e;
	}

	public String newS(final String newS) {
		this.s = newS;
		return this.s;
	}

	public String getE() {
		return this.e;
	}

	public String getS() {
		return this.s;
	}

	public String getSystem() {
		return this.system;
	}

	public String newSystem(final String newSystem) {
		this.system = newSystem;
		return this.system;
	}

	public String getPhone() {
		return this.phone;
	}

	public String newPhone(final String phoneNew) {
		this.phone = phoneNew;
		return this.phone;
	}

	public String getCountry() {
		return this.country;
	}

	public void newCountry(final String country) {
		this.country = country;
	}

}
