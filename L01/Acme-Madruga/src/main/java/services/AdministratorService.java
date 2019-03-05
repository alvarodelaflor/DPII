
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.MessageBox;

/*
 * CONTROL DE CAMBIOS AdministratorService.java
 * 
 * Antonio Salvat 23/02/2019 19:49 Modifico create
 */

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private MessageBoxService		messageBoxService;


	public Administrator create() {
		final Administrator administrator = new Administrator();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		administrator.setUserAccount(user);

		final MessageBox inBox = this.messageBoxService.create();
		final MessageBox outBox = this.messageBoxService.create();
		final MessageBox trashBox = this.messageBoxService.create();
		final MessageBox notificationBox = this.messageBoxService.create();
		final MessageBox spamBox = this.messageBoxService.create();

		inBox.setName("in box");
		outBox.setName("out box");
		trashBox.setName("trash box");
		notificationBox.setName("notification box");
		spamBox.setName("spam box");

		inBox.setIsDefault(true);
		outBox.setIsDefault(true);
		trashBox.setIsDefault(true);
		notificationBox.setIsDefault(true);
		spamBox.setIsDefault(true);

		final MessageBox inBoxSave = this.messageBoxService.save(inBox);
		final MessageBox outBoxSave = this.messageBoxService.save(outBox);
		final MessageBox trashBoxSave = this.messageBoxService.save(trashBox);
		final MessageBox notificationBoxSave = this.messageBoxService.save(notificationBox);
		final MessageBox spamBoxSave = this.messageBoxService.save(spamBox);

		final Collection<MessageBox> boxesDefault = new ArrayList<>();

		boxesDefault.add(inBoxSave);
		boxesDefault.add(outBoxSave);
		boxesDefault.add(trashBoxSave);
		boxesDefault.add(notificationBoxSave);
		boxesDefault.add(spamBoxSave);

		administrator.setMessageBoxes(boxesDefault);
		administrator.setIsBanned(false);
		administrator.setIsSuspicious(false);

		return administrator;
	}

	public List<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int id) {
		final Administrator administrator = this.administratorRepository.findOne(id);
		return administrator;
	}

	public Administrator save(final Administrator administrator) {
		return this.administratorRepository.save(administrator);
	}

	public Administrator update(final Administrator administrator) {
		Assert.isTrue(LoginService.getPrincipal().getId() == administrator.getUserAccount().getId());
		return this.administratorRepository.save(administrator);
	}

	public Administrator getAdministratorByUserAccountId(final int userAccountId) {
		Administrator res;
		res = this.administratorRepository.findByUserAccountId(userAccountId);
		return res;
	}

}
