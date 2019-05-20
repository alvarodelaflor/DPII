
package services;

/**
 * MiscellaneousAttachmentService.java
 * 
 * @author Alvaro de la Flor Bonilla GitHub: alvar017
 * 
 *         CONTROL:
 *         30/03/2019 16:47 Creation
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousAttachmentRepository;
import domain.Curricula;
import domain.MiscellaneousAttachment;
import domain.Cleaner;

@Service
@Transactional
public class MiscellaneousAttachmentService {

	@Autowired
	private MiscellaneousAttachmentRepository	miscellaneousAttachmentRepository;

	@Autowired
	private CleanerService						cleanerService;


	// CRUD Methods

	/**
	 * Create a {@link MiscellaneousAttachment} instance<br>
	 * Must exist an {@link Cleaner} login
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */
	public MiscellaneousAttachment create() {
		Assert.notNull(this.cleanerService.getCleanerLogin());
		return new MiscellaneousAttachment();
	}

	/**
	 * Crete a new MiscellaneousAttachment with the given curricula<br>
	 * Atributes isCopy: false and the curricula given are setted by default
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */
	public MiscellaneousAttachment createWithHistory(final Curricula curricula) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		final Cleaner cleanerCurricula = curricula.getCleaner();
		Assert.notNull(cleanerCurricula, "No cleaner for this curricula");
		Assert.isTrue(cleanerCurricula.equals(cleanerLogin), "Login and cleaner curricula are diferent");
		final MiscellaneousAttachment res = new MiscellaneousAttachment();
		res.setCurriculaM(curricula);
		res.setIsCopy(false);
		return res;
	}

	/**
	 * Save a MiscellaneousAttachment given <br>
	 * 
	 * Must be a {@link Cleaner} login and the owner of the educationalDate to save must be this cleaner
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */
	public MiscellaneousAttachment save(final MiscellaneousAttachment miscellaneousAttachment) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner login");
		Assert.notNull(miscellaneousAttachment, "Null miscellaneousAttachment");
		Assert.isTrue(cleanerLogin.equals(miscellaneousAttachment.getCurriculaM().getCleaner()), "Not allow to edit not own MiscellaneousAttachment");
		return this.miscellaneousAttachmentRepository.save(miscellaneousAttachment);
	}

	/**
	 * Save a collection of MiscellaneousAttachment given <br>
	 * 
	 * Must be a {@link Cleaner} login and the owner of the educationalDate to save must be this cleaner
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}<{@link MiscellaneousAttachment}>
	 */
	public Collection<MiscellaneousAttachment> savaAll(final Collection<MiscellaneousAttachment> educationalsData) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		Assert.isTrue(!educationalsData.isEmpty(), "Empty collection of educationalsData");
		Assert.isTrue(educationalsData.iterator().next().getCurriculaM().getCleaner().equals(cleanerLogin), "No valid cleaner to save");
		return this.miscellaneousAttachmentRepository.save(educationalsData);
	}

	/**
	 * Find a MiscellaneousAttachment in the DataBase
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */
	public MiscellaneousAttachment findOne(final int miscellaneousAttachmentId) {
		return this.miscellaneousAttachmentRepository.findOne(miscellaneousAttachmentId);
	}

	/**
	 * Delete a MiscellaneousAttachment given <br>
	 * 
	 * Must be a {@link Cleaner} login and the owner of the educationalDate to delete must be this cleaner
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final MiscellaneousAttachment miscellaneousAttachment) {
		Assert.notNull(miscellaneousAttachment);
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		Assert.isTrue(miscellaneousAttachment.getCurriculaM().getCleaner().equals(cleanerLogin), "Not allow to delete a miscellaneousAttachment of another cleaner");
		this.miscellaneousAttachmentRepository.delete(miscellaneousAttachment);
	}

	/**
	 * Delete a Collection of MiscellaneousAttachment given <br>
	 * 
	 * Must be a {@link Cleaner} login and the owner of the educationalDate to delete must be this cleaner
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void deleteAll(final Collection<MiscellaneousAttachment> miscellaneousAttachments) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin);
		Assert.isTrue(cleanerLogin.equals(((List<MiscellaneousAttachment>) miscellaneousAttachments).get(0).getCurriculaM().getCleaner()), "Diferent cleaners");
		if (!miscellaneousAttachments.isEmpty())
			this.miscellaneousAttachmentRepository.delete(miscellaneousAttachments);
	}

	// CRUD Methods

	// AUXILIAR METHODS

	/**
	 * @return The reconstruct curricula
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<MiscellaneousAttachment> getMiscellaneousAttachmentFromCurricula(final Curricula curricula) {
		return this.miscellaneousAttachmentRepository.getMiscellaneousAttachmentFromCurriculaId(curricula.getId());
	}

	public void makeCopyAllMiscellaneousAttachmentForCurricula(final Curricula origen, final Curricula copy) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		Assert.isTrue(origen.getIsCopy().equals(false) && copy.getIsCopy().equals(true), "Origen can not be copyMode and copy must be copyMode");
		Assert.isTrue(origen.getCleaner().equals(copy.getCleaner()), "Diferent cleaner origen-copy");
		Assert.isTrue(origen.getCleaner().equals(cleanerLogin), "Diferent cleaner origen-login");
		final List<MiscellaneousAttachment> educationalsData = (List<MiscellaneousAttachment>) this.miscellaneousAttachmentRepository.getMiscellaneousAttachmentFromCurriculaId(origen.getId());
		if (!educationalsData.isEmpty()) {
			final Collection<MiscellaneousAttachment> educationalsDataCopy = new ArrayList<>();
			for (final MiscellaneousAttachment miscellaneousAttachment : educationalsData)
				educationalsDataCopy.add(this.getCopy(miscellaneousAttachment, copy));
			this.savaAll(educationalsDataCopy);
		}
	}

	public MiscellaneousAttachment getCopy(final MiscellaneousAttachment miscellaneousAttachment, final Curricula copy) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		Assert.isTrue(miscellaneousAttachment.getCurriculaM().getCleaner().equals(copy.getCleaner()));
		Assert.isTrue(cleanerLogin.equals(miscellaneousAttachment.getCurriculaM().getCleaner()), "Cleaner curricula is diferent to cleaner miscellaneousAttachment");
		final MiscellaneousAttachment miscellaneousAttachmentCopy = this.create();
		miscellaneousAttachmentCopy.setCurriculaM(copy);
		miscellaneousAttachmentCopy.setAttachment(miscellaneousAttachment.getAttachment());
		miscellaneousAttachmentCopy.setIsCopy(true);
		return miscellaneousAttachmentCopy;
	}

	public Boolean checkDate(final Date startDate, final Date endDate) {
		Boolean res = true;
		if (startDate.before(endDate))
			res = false;
		return res;
	}

	// AUXILIAR METHODS
}
