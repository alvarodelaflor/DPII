package services;

/**
 * CurriculaService.java
 * 
 * @author Álvaro de la Flor Bonilla GitHub: alvar017
 * 
 * CONTROL:
 * 30/03/2019 14:47 Creation
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Curricula;
import domain.EducationalData;
import domain.Hacker;
import domain.PositionData;
import repositories.CurriculaRepository;
import security.LoginService;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository	curriculaRepository;
	
	@Autowired
	private HackerService hackerService;
	
	@Autowired
	private EducationalDataService educationalDataService;
	
	@Autowired
	private PositionDataService positionDataService;

	@Autowired
	private Validator					validator;

	// CRUD Methods

	
	/**
	 * Create a CurriculaEntity. Must exist an hacker login to create it.
	 * @author Álvaro de la Flor Bonilla
	 * @return A curricula entity
	 */
	public Curricula create() {
		Assert.isTrue(checkAnyLogger(), "Must exist an user login");
		Assert.notNull(hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId()), "Must exist an hacker login");
		return new Curricula();
	}
	
	/**
	 * 
	 * @author Álvaro de la Flor Bonilla
	 * @return Return all Curricula in dataBase
	 */	
	public List<Curricula> findAll() {
		return this.curriculaRepository.findAll();
	}
	
	/**
	 * Find a curricula in dataBase by curriculaId
	 * @author Álvaro de la Flor Bonilla
	 * @return A curricula entity by curriculaId
	 */	
	public Curricula findOne(int curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}
	
	/**
	 * Save a curricula. Check user login is the same that user who create the curricula
	 * 
	 * @author Álvaro de la Flor Bonilla
	 * @return The save curricula
	 */
	public Curricula save(Curricula curricula) {
		Assert.notNull(curricula, "Curricula is null");
		Assert.isTrue(checkAnyLogger(), "Any user is login");
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Curricula curriculaDB = this.curriculaRepository.findOne(curricula.getId());
		if (curriculaDB != null) {
			Assert.isTrue(curricula.getHacker().equals(hackerLogin), "Not allow to edit a not own curricula");
		}
		return this.curriculaRepository.save(curricula);
	}
	
	/**
	 * Save a curricula. Check user login is the same that user who want to save the curricula
	 * 
	 * @author Álvaro de la Flor Bonilla
	 */
	public void delete(Curricula curricula) {
		Assert.isTrue(checkAnyLogger(), "Any user is login");
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Curricula curriculaDB = this.curriculaRepository.findOne(curricula.getId());
		Assert.notNull(curriculaDB, "The curricula is not in DB");
		Assert.isTrue(curricula.getHacker().equals(hackerLogin), "Not allow to delete a not own curricula");
		Collection<EducationalData> educationalDatas = this.educationalDataService.getEducationalDataFromCurricula(curriculaDB);
		if (!educationalDatas.isEmpty()) {
			this.educationalDataService.deleteAll(educationalDatas);			
		}
		Collection<PositionData> positionDatas = this.positionDataService.getPositionDataFromCurricula(curriculaDB);
		if (!positionDatas.isEmpty()) {
			this.positionDataService.deleteAll(positionDatas);			
		}
		this.curriculaRepository.delete(curricula);
	}

	// CRUD Methods
	
	// AUXILIAR METHODS
	
	/**
	 * @return The reconstruct curricula
	 * @author Alvaro de la Flor Bonilla
	 */
	public Curricula reconstruct(Curricula curricula, BindingResult binding) {
		Curricula result;

		if (curricula.getId() == 0) {
			curricula.setHacker(this.hackerService.getHackerLogin());
			curricula.setIsCopy(false);
			result = curricula;
		} else {
			result = this.curriculaRepository.findOne(curricula.getId());
			Assert.notNull(result, "Float is null");
			curricula.setId(result.getId());
			curricula.setVersion(result.getVersion());
			curricula.setHacker(result.getHacker());
			curricula.setIsCopy(result.getIsCopy());
			result = curricula;
		}
		this.validator.validate(result, binding);
		return result;
	}

	/**
	 * 
	 * This methods create an copy of curricula and all of their educationalData and positionData
	 * 
	 * @return The reconstruct curricula
	 * @author Alvaro de la Flor Bonilla
	 */
	public Curricula createCopyAndSave(Curricula curricula) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.isTrue(curricula.getHacker().equals(hackerLogin), "Try to do a copy of curricula by not own hacker");
		Curricula curriculaCopy = this.create();
		curriculaCopy.setHacker(curricula.getHacker());
		curriculaCopy.setIsCopy(true);
		curriculaCopy.setLinkGitHub(curricula.getLinkGitHub());
		curriculaCopy.setLinkGitHub(curricula.getLinkGitHub());
		curriculaCopy.setMiscellaneous(curricula.getMiscellaneous());
		curriculaCopy.setName(curricula.getName());
		curriculaCopy.setPhone(curricula.getPhone());
		curriculaCopy.setStatement(curricula.getStatement());
		Curricula copy = this.save(curriculaCopy);
		this.educationalDataService.makeCopyAllEducationalDataForCurricula(curricula, copy);
		this.positionDataService.makeCopyAllPositionDataForCurricula(curricula, copy);
		return copy;
	}
	
	/**
	 * @author Alvaro de la Flor Bonilla
	 */
	public void flush() {
		this.curriculaRepository.flush();
	}
	
	/**
	 * Check that any user is login
	 * 
	 * @author Álvaro de la Flor Bonilla
	 * @return True if an user is login, false in otherwise.
	 */
	public Boolean checkAnyLogger() {
		Boolean res = true;
		try {
			LoginService.getPrincipal().getId();
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	/**
	 * 
	 * @author Álvaro de la Flor Bonilla
	 * @return Return all Curricula in dataBase of a hacker.
	 */	
	public Collection<Curricula> findAllByHacker(Hacker hacker) {
		List<Curricula> res = new ArrayList<>();
		if (hacker!=null) {
			res = (List<Curricula>) this.curriculaRepository.getCurriculasOfHacker(hacker.getId()); 
		}
		return res;
	}
	
	/**
	 * 
	 * @author Álvaro de la Flor Bonilla
	 * @return {@link Collection < {@link Curricula} > not copy mode
	 */	
	public Collection<Curricula> findAllNotCopyByHacker(Hacker hacker) {
		List<Curricula> res = new ArrayList<>();
		if (hacker!=null) {
			res = (List<Curricula>) this.curriculaRepository.getCurriculasNotCopyOfHacker(hacker.getId()); 
		}
		return res;
	}
	// AUXILIAR METHODS
}
