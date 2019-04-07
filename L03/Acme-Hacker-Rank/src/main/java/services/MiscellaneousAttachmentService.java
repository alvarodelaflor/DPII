package services;

/**
 * MiscellaneousAttachmentService.java
 * 
 * @author Alvaro de la Flor Bonilla GitHub: alvar017
 * 
 * CONTROL:
 * 30/03/2019 16:47 Creation
 */

import java.util.ArrayList;

/**
 * PositionDataServie.java
 * 
 * @author Álvaro de la Flor Bonilla GitHub: alvar017
 * 
 * CONTROL:
 * 30/03/2019 16:35 Creation
 */

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import domain.Curricula;
import domain.MiscellaneousAttachment;
import domain.Hacker;
import repositories.MiscellaneousAttachmentRepository;

@Service
@Transactional
public class MiscellaneousAttachmentService {

	@Autowired
	private MiscellaneousAttachmentRepository miscellaneousAttachmentRepository;
	
	@Autowired
	private HackerService hackerService;

	// CRUD Methods

	/**
	 * Create a {@link MiscellaneousAttachment} instance<br>
	 * Must exist an {@link Hacker} login
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */
	public MiscellaneousAttachment create() {
		Assert.notNull(this.hackerService.getHackerLogin());
		return new MiscellaneousAttachment();
	}
	
	/**
	 * Crete a new MiscellaneousAttachment with the given curricula<br> Atributes isCopy: false and the curricula given are setted by default
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */
	public MiscellaneousAttachment createWithHistory(Curricula curricula) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Hacker hackerCurricula = this.hackerService.getHackerByCurriculaId(curricula);
		Assert.notNull(hackerCurricula, "No hacker for this curricula");
		Assert.isTrue(hackerCurricula.equals(hackerLogin), "Login and hacker curricula are diferent");
		MiscellaneousAttachment res = new MiscellaneousAttachment();
		res.setCurriculaM(curricula);
		res.setIsCopy(false);
		return res;
	}

	/**
	 * Save a MiscellaneousAttachment  given <br> 
	 * 
	 * Must be a {@link Hacker} login and the owner of the educationalDate to save must be this hacker
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */
	public MiscellaneousAttachment save(MiscellaneousAttachment miscellaneousAttachment) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker login");
		Assert.notNull(miscellaneousAttachment, "Null miscellaneousAttachment");
		Assert.isTrue(hackerLogin.equals(miscellaneousAttachment.getCurriculaM().getHacker()), "Not allow to edit not own MiscellaneousAttachment");
		return this.miscellaneousAttachmentRepository.save(miscellaneousAttachment);
	}
	
	/**
	 * Save a collection of MiscellaneousAttachment given <br> 
	 * 
	 * Must be a {@link Hacker} login and the owner of the educationalDate to save must be this hacker
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}<{@link MiscellaneousAttachment}>
	 */	
	public Collection<MiscellaneousAttachment> savaAll(Collection<MiscellaneousAttachment> educationalsData) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(!educationalsData.isEmpty(), "Empty collection of educationalsData");
		Assert.isTrue(educationalsData.iterator().next().getCurriculaM().getHacker().equals(hackerLogin), "No valid hacker to save");
		return this.miscellaneousAttachmentRepository.save(educationalsData);
	}

	/**
	 * Find a MiscellaneousAttachment in the DataBase 
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */	
	public MiscellaneousAttachment findOne(int miscellaneousAttachmentId) {
		return this.miscellaneousAttachmentRepository.findOne(miscellaneousAttachmentId);
	}
	
	/**
	 * Delete a MiscellaneousAttachment given <br> 
	 * 
	 * Must be a {@link Hacker} login and the owner of the educationalDate to delete must be this hacker
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */	
	public void delete(MiscellaneousAttachment miscellaneousAttachment) {
		Assert.notNull(miscellaneousAttachment);
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(miscellaneousAttachment.getCurriculaM().getHacker().equals(hackerLogin), "Not allow to delete a miscellaneousAttachment of another hacker");
		this.miscellaneousAttachmentRepository.delete(miscellaneousAttachment);
	}
	
	/**
	 * Delete a Collection of MiscellaneousAttachment given <br> 
	 * 
	 * Must be a {@link Hacker} login and the owner of the educationalDate to delete must be this hacker
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */	
	public void deleteAll(Collection<MiscellaneousAttachment> miscellaneousAttachments) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin);
		Assert.isTrue(hackerLogin.equals(this.hackerService.getHackerByCurriculaId(((List<MiscellaneousAttachment>) miscellaneousAttachments).get(0).getCurriculaM())), "Diferent hackers");
		if (!miscellaneousAttachments.isEmpty()) {
			this.miscellaneousAttachmentRepository.delete(miscellaneousAttachments);
		}
	}
	
	
	
	// CRUD Methods
	
	// AUXILIAR METHODS
	
	/**
	 * @return The reconstruct curricula
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<MiscellaneousAttachment> getMiscellaneousAttachmentFromCurricula(Curricula curricula) {
		return this.miscellaneousAttachmentRepository.getMiscellaneousAttachmentFromCurriculaId(curricula.getId());
	}
	
	public void makeCopyAllMiscellaneousAttachmentForCurricula(Curricula origen, Curricula copy) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(origen.getIsCopy().equals(false) && copy.getIsCopy().equals(true), "Origen can not be copyMode and copy must be copyMode");
		Assert.isTrue(origen.getHacker().equals(copy.getHacker()), "Diferent hacker origen-copy");
		Assert.isTrue(origen.getHacker().equals(hackerLogin), "Diferent hacker origen-login");
		List<MiscellaneousAttachment> educationalsData = (List<MiscellaneousAttachment>) this.miscellaneousAttachmentRepository.getMiscellaneousAttachmentFromCurriculaId(origen.getId());
		if (!educationalsData.isEmpty()) {
			Collection<MiscellaneousAttachment> educationalsDataCopy = new ArrayList<>();
			for (MiscellaneousAttachment miscellaneousAttachment : educationalsData) {
				educationalsDataCopy.add(getCopy(miscellaneousAttachment, copy));
			}
			this.savaAll(educationalsDataCopy);	
		}
	}
	
	public MiscellaneousAttachment getCopy(MiscellaneousAttachment miscellaneousAttachment, Curricula copy) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(miscellaneousAttachment.getCurriculaM().getHacker().equals(copy.getHacker()));
		Assert.isTrue(hackerLogin.equals(miscellaneousAttachment.getCurriculaM().getHacker()), "Hacker curricula is diferent to hacker miscellaneousAttachment");
		MiscellaneousAttachment miscellaneousAttachmentCopy = this.create();
		miscellaneousAttachmentCopy.setCurriculaM(copy);
		miscellaneousAttachmentCopy.setAttachment(miscellaneousAttachment.getAttachment());
		miscellaneousAttachmentCopy.setIsCopy(true);
		return miscellaneousAttachmentCopy;
	}
	
	public Boolean checkDate(Date startDate, Date endDate) {
		Boolean res = true;
		if (startDate.before(endDate)) {
			res = false;
		}
		return res;
	}

	// AUXILIAR METHODS
}
