
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Cleaner;
import domain.CleaningTask;
import repositories.CleaningTaskRepository;

@Service
@Transactional
public class CleaningTaskService {

	//Managed repository
	@Autowired
	private CleaningTaskRepository	cleaningTaskRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;


	public CleaningTask reconstruct(final CleaningTask cleaningTask, final BindingResult binding) {
		CleaningTask result;

		if (cleaningTask.getId() == 0)
			result = cleaningTask;
		else {
			result = this.findOne(cleaningTask.getId());
			cleaningTask.setAccomodation(result.getAccomodation());
			cleaningTask.setCleaner(result.getCleaner());
			cleaningTask.setEndMoment(result.getEndMoment());
			cleaningTask.setStartMoment(result.getStartMoment());
			cleaningTask.setDescription(result.getDescription());
			result = cleaningTask;
		}
		this.validator.validate(cleaningTask, binding);
		return result;
	}
	public CleaningTask create() {
		final CleaningTask cleaningTask = new CleaningTask();
		return cleaningTask;
	}

	public void delete(final CleaningTask cleaningTask) {
		this.cleaningTaskRepository.delete(cleaningTask);

	}

	public CleaningTask findOne(final int id) {
		return this.cleaningTaskRepository.findOne(id);
	}

	public Collection<CleaningTask> findAll(final int id) {
		return this.cleaningTaskRepository.findAll();
	}

	public CleaningTask save(final CleaningTask cleaningTask) {
		return this.cleaningTaskRepository.save(cleaningTask);
	}

	public Collection<CleaningTask> getCleaningTaskHost(final int id) {
		return this.cleaningTaskRepository.getCleaningTaskHost(id);
	}

	public Boolean checkDate(final Date startDate, final Date endDate) {
		Boolean res = true;
		if (startDate.before(endDate))
			res = false;
		return res;
	}
	public void deleteCleanerTasks(final Cleaner cleaner) {
		final Collection<CleaningTask> tasks = this.getCleanerCleaningTasks(cleaner.getId());
		if (tasks != null && !tasks.isEmpty())
			for (final CleaningTask task : tasks)
				this.delete(task);
	}
	private Collection<CleaningTask> getCleanerCleaningTasks(final int id) {
		return this.cleaningTaskRepository.getCleanerCleaningTaks(id);
	}

}
