
package services;

import java.util.HashSet;

import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class WelcomeService {

	String					e			= "Welcome to Acme Madrug·°, the site to organise your parades.";

	String					s			= "°Bienvenidos a Acme Madrug·°! Tu sitio para organizar procesiones.";

	String					system		= "Acme Madrug·°";

	String					phone		= "34";

	String					country		= "Espa≈Ña/Spain";

	@URL
	String					logo		= "https://tinyurl.com/acme-madruga";

	public HashSet<String>	spamWords	= new HashSet<>();

	public HashSet<String>	priorities	= new HashSet<>();


	//M√©todo para a≈Ñadir prioridades
	public HashSet<String> addPriority(final String priority) {
		this.priorities.add(priority);
		return this.priorities;
	}

	//Metodo para quitar las prioridades
	public HashSet<String> deletePriority(final String priority) {
		this.priorities.remove(priority);
		Assert.isTrue(this.getPriorities().contains(priority), "noPriority.error");
		return this.priorities;
	}
	//Metodo prioridades 
	public HashSet<String> defaultPriorities() {

		this.priorities.add("HIGH");
		this.priorities.add("NEUTRAL");
		this.priorities.add("LOW");

		return this.priorities;
	}

	public HashSet<String> getPriorities() {
		return this.priorities;
	}

	//Carmen: M√©todo para a≈Ñadir spam words (adm)
	public HashSet<String> newSpamWords(final String newWord) {
		this.spamWords.add(newWord);
		return this.spamWords;
	}

	public HashSet<String> getSpamWords() {
		return this.spamWords;
	}

	public HashSet<String> deleteSpamWords(final String newWord) {
		Assert.isTrue(this.getSpamWords().contains(newWord), "noSpamWord.error");
		this.spamWords.remove(newWord);
		return this.spamWords;
	}

	//Carmen: M√©todo para mostrar las spam words
	public HashSet<String> listSpamWords() {

		this.spamWords.add("sex");
		this.spamWords.add("viagra");
		this.spamWords.add("cialis");
		this.spamWords.add("one millon");
		this.spamWords.add("you've been selected");
		this.spamWords.add("Nigeria");
		this.spamWords.add("sexo");
		this.spamWords.add("un mill√≥n");
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
