
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
	private RefereeService		refereeService;


	public Collection<Review> findReviewRefereeLogged() {
		final Referee referee = this.refereeService.getRefereeByUserAccountId(LoginService.getPrincipal().getId());
		return this.reviewRepository.getReviewReferee(referee.getId());
	}

	public Review create() {
		final Review review = new Review();

		return review;
	}

	public Review findOne(final int id) {
		final Referee referee = this.refereeService.getRefereeByUserAccountId(LoginService.getPrincipal().getId());
		final Review review = this.reviewRepository.findOne(id);
		Assert.isTrue(review.getReferee().equals(referee));
		return this.reviewRepository.findOne(id);
	}

	public Review save(final Review review) {
		review.setMoment(LocalDateTime.now().toDate());
		final Referee referee = this.refereeService.getRefereeByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(referee != null);
		review.setReferee(referee);
		return this.reviewRepository.save(review);
	}

	public void flush() {
		this.reviewRepository.flush();
	}

	public void deleteAllByReferee(final Referee referee) {
		final Collection<Review> items = this.findReviewRefereeLogged();
		if (items != null && !items.isEmpty())
			for (final Review item : items)
				this.delete(item);
	}

	private void delete(final Review item) {
		this.reviewRepository.delete(item);
	}

}
