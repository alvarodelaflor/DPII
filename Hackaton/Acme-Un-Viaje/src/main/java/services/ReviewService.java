package services;


import java.util.Collection;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Cleaner;
import domain.CleaningTask;
import domain.Host;
import domain.Referee;
import domain.Review;
import repositories.ReviewRepository;
import security.LoginService;

@Service
@Transactional
public class ReviewService {
	
	@Autowired
	private ReviewRepository	reviewRepository;
	
	@Autowired
	private RefereeService	refereeService;
	
	public Collection<Review> findReviewRefereeLogged( ) {
		Referee referee = refereeService.getRefereeByUserAccountId(LoginService.getPrincipal().getId());	
		return reviewRepository.getReviewReferee(referee.getId());
	}
	
	public Review create() {
		final Review review = new Review();
		
		return review;
	}
	
	public Review findOne(int id) {
		Referee referee = refereeService.getRefereeByUserAccountId(LoginService.getPrincipal().getId());
		Review review = reviewRepository.findOne(id);
		Assert.isTrue(review.getReferee().equals(referee));
		return reviewRepository.findOne(id);
	}
	
	public Review  save(Review review) {
		review.setMoment(LocalDateTime.now().toDate());
		Referee referee = refereeService.getRefereeByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(referee != null);
		review.setReferee(referee);
		return reviewRepository.save(review);
	}
	
	public void flush() {
		this.reviewRepository.flush();
	}

}
