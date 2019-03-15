
package services;

/*
 * CONTROL DE CAMBIOS AdministratorService.java
 * FRAN 19/02/2019 11:36 CREACIÓN DE LA CLASE
 */

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Chapter;
import domain.Proclaim;
import repositories.ProclaimRepository;
import security.LoginService;

@Service
@Transactional
public class ProclaimService {
	
	@Autowired
	private ChapterService chapterService;

	@Autowired
	private ProclaimRepository	proclaimRepository;

	//Simple CRUD Methods ------------------
	
	public Proclaim create() {
		Assert.notNull(chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId()));
		final Proclaim proclaim = new Proclaim();
		return proclaim;
	}

	public Proclaim findOne(final int id) {
		return this.proclaimRepository.findOne(id);
	}
	
	public Proclaim save(final Proclaim proclaimRecord) {
		Assert.notNull(proclaimRecord, "procleimRecordSaveService.null");
		Chapter chapter = this.chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(chapter, "chapterLoggerNull");
		Proclaim proclaimDB = this.proclaimRepository.findOne(proclaimRecord.getId());
		Assert.isTrue(proclaimDB==null, "Try to edit a save proclaim");
		Proclaim proclaimSaved = this.proclaimRepository.save(proclaimRecord);
		chapter.getProclaim().add(proclaimSaved);
		return proclaimSaved;
	}
}
