
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
import domain.Rookie;

@Service
@Transactional
public class MiscellaneousAttachmentService {

	@Autowired
	private MiscellaneousAttachmentRepository	miscellaneousAttachmentRepository;

	@Autowired
	private RookieService						rookieService;


	// CRUD Methods

	/**
	 * Create a {@link MiscellaneousAttachment} instance<br>
	 * Must exist an {@link Rookie} login
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */
	public MiscellaneousAttachment create() {
		Assert.notNull(this.rookieService.getRookieLogin());
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
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		final Rookie rookieCurricula = this.rookieService.getRookieByCurriculaId(curricula);
		Assert.notNull(rookieCurricula, "No rookie for this curricula");
		Assert.isTrue(rookieCurricula.equals(rookieLogin), "Login and rookie curricula are diferent");
		final MiscellaneousAttachment res = new MiscellaneousAttachment();
		res.setCurriculaM(curricula);
		res.setIsCopy(false);
		return res;
	}

	/**
	 * Save a MiscellaneousAttachment given <br>
	 * 
	 * Must be a {@link Rookie} login and the owner of the educationalDate to save must be this rookie
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}
	 */
	public MiscellaneousAttachment save(final MiscellaneousAttachment miscellaneousAttachment) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie login");
		Assert.notNull(miscellaneousAttachment, "Null miscellaneousAttachment");
		Assert.isTrue(rookieLogin.equals(miscellaneousAttachment.getCurriculaM().getRookie()), "Not allow to edit not own MiscellaneousAttachment");
		return this.miscellaneousAttachmentRepository.save(miscellaneousAttachment);
	}

	/**
	 * Save a collection of MiscellaneousAttachment given <br>
	 * 
	 * Must be a {@link Rookie} login and the owner of the educationalDate to save must be this rookie
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link MiscellaneousAttachment}<{@link MiscellaneousAttachment}>
	 */
	public Collection<MiscellaneousAttachment> savaAll(final Collection<MiscellaneousAttachment> educationalsData) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(!educationalsData.isEmpty(), "Empty collection of educationalsData");
		Assert.isTrue(educationalsData.iterator().next().getCurriculaM().getRookie().equals(rookieLogin), "No valid rookie to save");
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
	 * Must be a {@link Rookie} login and the owner of the educationalDate to delete must be this rookie
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final MiscellaneousAttachment miscellaneousAttachment) {
		Assert.notNull(miscellaneousAttachment);
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(miscellaneousAttachment.getCurriculaM().getRookie().equals(rookieLogin), "Not allow to delete a miscellaneousAttachment of another rookie");
		this.miscellaneousAttachmentRepository.delete(miscellaneousAttachment);
	}

	/**
	 * Delete a Collection of MiscellaneousAttachment given <br>
	 * 
	 * Must be a {@link Rookie} login and the owner of the educationalDate to delete must be this rookie
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void deleteAll(final Collection<MiscellaneousAttachment> miscellaneousAttachments) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin);
		Assert.isTrue(rookieLogin.equals(this.rookieService.getRookieByCurriculaId(((List<MiscellaneousAttachment>) miscellaneousAttachments).get(0).getCurriculaM())), "Diferent rookies");
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
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(origen.getIsCopy().equals(false) && copy.getIsCopy().equals(true), "Origen can not be copyMode and copy must be copyMode");
		Assert.isTrue(origen.getRookie().equals(copy.getRookie()), "Diferent rookie origen-copy");
		Assert.isTrue(origen.getRookie().equals(rookieLogin), "Diferent rookie origen-login");
		final List<MiscellaneousAttachment> educationalsData = (List<MiscellaneousAttachment>) this.miscellaneousAttachmentRepository.getMiscellaneousAttachmentFromCurriculaId(origen.getId());
		if (!educationalsData.isEmpty()) {
			final Collection<MiscellaneousAttachment> educationalsDataCopy = new ArrayList<>();
			for (final MiscellaneousAttachment miscellaneousAttachment : educationalsData)
				educationalsDataCopy.add(this.getCopy(miscellaneousAttachment, copy));
			this.savaAll(educationalsDataCopy);
		}
	}

	public MiscellaneousAttachment getCopy(final MiscellaneousAttachment miscellaneousAttachment, final Curricula copy) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(miscellaneousAttachment.getCurriculaM().getRookie().equals(copy.getRookie()));
		Assert.isTrue(rookieLogin.equals(miscellaneousAttachment.getCurriculaM().getRookie()), "Rookie curricula is diferent to rookie miscellaneousAttachment");
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
