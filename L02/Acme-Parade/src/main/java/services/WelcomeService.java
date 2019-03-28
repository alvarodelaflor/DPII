
package services;

import java.util.HashSet;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import security.LoginService;
import domain.Administrator;

@Service
public class WelcomeService {

	String					e			= "Welcome to Acme Parade, the site to organise your parades.";

	String					s			= "¡Bienvenidos a Acme Parade! Tu sitio para organizar desfiles.";

	String							system				= "Acme Madruga";

	String							phone				= "34";

	String							country				= "España/Spain";

	@URL
	String							logo				= "http://oi63.tinypic.com/11invgm.jpg";

	public HashSet<String>			spamWords			= new HashSet<>();

	public HashSet<String>			priorities			= new HashSet<>();

	public HashSet<String>			creditCardsMakes	= new HashSet<>();

	@Autowired
	private AdministratorService	adminService;


	// CreditCardMakes:
	// ------------------------------------------------------------
	//Metodo para agregar CCMake
	public HashSet<String> addCCMake(final String make) {

		// "Check for Admin logged"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.error");

		this.creditCardsMakes.add(make);
		return this.creditCardsMakes;
	}
	//Metodo para quitar CCMake
	public HashSet<String> removeCCMake(final String make) {

		// "Check for Admin logged"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.error");

		Assert.isTrue(this.getCreditCardsMakes().contains(make), "noCCMake.error");
		this.creditCardsMakes.remove(make);
		return this.creditCardsMakes;
	}
	//Metodo CCsMakes por defecto
	public HashSet<String> defaultCCsMakes() {

		this.creditCardsMakes.add("VISA");
		this.creditCardsMakes.add("MCARD");
		this.creditCardsMakes.add("AMEX");
		this.creditCardsMakes.add("DINNERS");
		this.creditCardsMakes.add("FLY");

		return this.creditCardsMakes;
	}

	public HashSet<String> getCreditCardsMakes() {

		return this.creditCardsMakes;
	}

	public void setCreditCardsMakes(final HashSet<String> creditCardsMakes) {

		this.creditCardsMakes = creditCardsMakes;
	}
	// ------------------------------------------------------------

	//Metodo para agregar prioridades
	public HashSet<String> addPriority(final String priority) {
		this.priorities.add(priority);
		return this.priorities;
	}

	//Metodo para quitar las prioridades
	public HashSet<String> deletePriority(final String priority) {
		Assert.isTrue(this.getPriorities().contains(priority), "noPriority.error");
		this.priorities.remove(priority);
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

	//Metodo para agregar spam words (adm)
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

	//Metodo para mostrar las spam words
	public HashSet<String> listSpamWords() {

		this.spamWords.add("sex");
		this.spamWords.add("viagra");
		this.spamWords.add("cialis");
		this.spamWords.add("one millon");
		this.spamWords.add("you've been selected");
		this.spamWords.add("Nigeria");
		this.spamWords.add("sexo");
		this.spamWords.add("un millÃ³n");
		this.spamWords.add("ha sido seleccionado");

		return this.spamWords;
	}

	public String getLogo() {
		return this.logo;
	}

	public String newLogo(final String newLogo) {
		this.logo = newLogo;
		return this.logo;
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
