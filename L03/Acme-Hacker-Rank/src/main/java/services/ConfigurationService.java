
package services;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Configuration;
import repositories.ConfigurationRepository;
import security.LoginService;

@Service
@Transactional
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository	configurationRepository;

	@Autowired
	private AdministratorService	administratorService;


	public Configuration getConfiguration() {

		return this.configurationRepository.findAll().iterator().next();
	}
	public Configuration save(final Configuration configuration) {

		return this.configurationRepository.save(configuration);
	}
	public void addPriority(final String newPriority) {
		Assert.notNull(LoginService.getPrincipal());
		Assert.isTrue(this.administratorService.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		final HashSet<String> priorities = new HashSet<>(this.getConfiguration().getPriorities());
		priorities.add(newPriority);
		final Configuration config = this.getConfiguration();
		config.setPriorities(priorities);
		this.configurationRepository.save(config);
	}
	public void deletePriority(final String deletePriority) {
		Assert.notNull(LoginService.getPrincipal());
		Assert.isTrue(this.administratorService.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		final HashSet<String> priorities = new HashSet<>(this.getConfiguration().getPriorities());
		priorities.remove(deletePriority);
		final Configuration config = this.getConfiguration();
		config.setPriorities(priorities);
		this.configurationRepository.save(config);
	}
	public void newPhone(final String newPhone) {
		Assert.notNull(LoginService.getPrincipal());
		Assert.isTrue(this.administratorService.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		final Configuration config = this.getConfiguration();
		config.setCountryCode(newPhone);
		this.configurationRepository.save(config);
	}
	public void newLogo(final String newLogo) {
		System.out.println(LoginService.getPrincipal());
		Assert.notNull(LoginService.getPrincipal());
		Assert.isTrue(this.administratorService.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		final Configuration config = this.getConfiguration();
		config.setBanner(newLogo);
		this.configurationRepository.save(config);
	}
	public void newSystem(final String newSystem) {
		Assert.notNull(LoginService.getPrincipal());
		Assert.isTrue(this.administratorService.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		final Configuration config = this.getConfiguration();
		config.setSystemName(newSystem);
		this.configurationRepository.save(config);
	}
	public void newE(final String newIngles) {
		Assert.isTrue(this.administratorService.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		final Configuration config = this.getConfiguration();
		config.setSystemMessageEn(newIngles);
		this.configurationRepository.save(config);
	}
	public void newS(final String newSpanish) {
		Assert.notNull(LoginService.getPrincipal());
		Assert.isTrue(this.administratorService.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		final Configuration config = this.getConfiguration();
		config.setSystemMessageEs(newSpanish);
		this.configurationRepository.save(config);
	}
	public void newSpamWords(final String newSpamWord) {
		Assert.notNull(LoginService.getPrincipal());
		Assert.isTrue(this.administratorService.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		final HashSet<String> spamWords = new HashSet<>(this.getConfiguration().getSpamWords());
		spamWords.add(newSpamWord);
		final Configuration config = this.getConfiguration();
		config.setSpamWords(spamWords);
		this.configurationRepository.save(config);

	}
	public void deleteSpamWords(final String newSpamWord) {
		Assert.isTrue(this.administratorService.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		final HashSet<String> spamWords = new HashSet<>(this.getConfiguration().getSpamWords());
		spamWords.remove(newSpamWord);
		final Configuration config = this.getConfiguration();
		config.setSpamWords(spamWords);
		this.configurationRepository.save(config);

	}
}
