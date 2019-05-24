package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Admin;
import domain.CleaningTask;
import domain.Mailbox;
import domain.Message;
import domain.Tag;
import repositories.CleaningTaskRepository;
import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;

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
			
			if (cleaningTask.getId() == 0) {
				result = cleaningTask;
			} else {
				result = findOne(cleaningTask.getId());
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
			cleaningTaskRepository.delete(cleaningTask);
			
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
		
}
