
package services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigRepository;
import security.LoginService;
import domain.Config;

@Service
@Transactional
public class ConfigService {

	@Autowired
	private ConfigRepository	configRepo;

	@Autowired
	private AdminService		adminService;


	public Config save(final Config config) {

		this.adminService.getAdminByUserAccountId(LoginService.getPrincipal().getId());

		return this.configRepo.save(config);
	}

	public void newSpamWord(final String word) {

		final Config config = this.adminService.getConfig();
		final HashSet<String> spamWords = new HashSet<>(config.getSpamList());
		spamWords.add(word);
		config.setSpamList(spamWords);
		this.configRepo.save(config);
	}
	public void deleteSpamWord(final String word) {

		final Config config = this.adminService.getConfig();
		final HashSet<String> spamWords = new HashSet<>(config.getSpamList());
		spamWords.remove(word);
		config.setSpamList(spamWords);
		this.configRepo.save(config);
	}

	public void newScoreWord(final String word) {

		final Config config = this.adminService.getConfig();
		final HashSet<String> scoreWords = new HashSet<>(config.getScoreList());
		scoreWords.add(word);
		config.setScoreList(scoreWords);
		this.configRepo.save(config);
	}

	public void deleteScoreWord(final String word) {

		final Config config = this.adminService.getConfig();
		final HashSet<String> scoreWords = new HashSet<>(config.getScoreList());
		scoreWords.remove(word);
		config.setScoreList(scoreWords);
		this.configRepo.save(config);
	}

	public void newCreditCardMake(final String word) {

		final Config config = this.adminService.getConfig();
		final HashSet<String> makes = new HashSet<>(config.getCreditCardMakeList());
		makes.add(word);
		config.setCreditCardMakeList(makes);
		this.configRepo.save(config);
	}

	public void deleteCreditCardMake(final String word) {

		final Config config = this.adminService.getConfig();
		final HashSet<String> makes = new HashSet<>(config.getCreditCardMakeList());
		makes.remove(word);
		config.setCreditCardMakeList(makes);
		this.configRepo.save(config);
	}
	
	public void newTransporterBanRatio(final int ratio) {

		Assert.isTrue(ratio > 0 && ratio < 100, "not.in.range.error");

		final Config config = this.adminService.getConfig();

		config.setTransporterBanRatio(ratio);
		this.configRepo.save(config);

	}

	public void newWelcomeMessageEn(final String msg) {

		final Config config = this.adminService.getConfig();
		config.setWelcomeMessageEn(msg);
		this.configRepo.save(config);
	}

	public void newWelcomeMessageEs(final String msg) {

		final Config config = this.adminService.getConfig();
		config.setWelcomeMessageEs(msg);
		this.configRepo.save(config);
	}

	public void newSystemNombre(final String msg) {

		final Config config = this.adminService.getConfig();
		config.setSystemNombre(msg);
		this.configRepo.save(config);
	}

	public void newSystemName(final String msg) {

		final Config config = this.adminService.getConfig();
		config.setSystemName(msg);
		this.configRepo.save(config);
	}

	public void newBanner(final String banner) {

		// From REGEX: ^(http(s?):\\/\\/www\\.).+(\\.)
		Assert.isTrue(banner.matches("^(http(s?):\\/\\/www\\.).+(\\.).+"), "not.url");
		final Config config = this.adminService.getConfig();
		config.setBannerLogo(banner);
		this.configRepo.save(config);
	}

	public void newDefaultPhoneCode(final String newdpc) {

		final Config config = this.adminService.getConfig();
		config.setDefaultPhoneCode(newdpc);
		this.configRepo.save(config);
	}

	// GET LOGO
	// ---------------------------------------------------------------
	public String getBannerLogo() {

		return this.configRepo.findAll().iterator().next().getBannerLogo();
	}
	
	public Config getConfiguration() {
		return this.configRepo.findAll().iterator().next();
	}

}
