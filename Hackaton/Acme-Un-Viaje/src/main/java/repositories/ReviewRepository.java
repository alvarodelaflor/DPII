package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;
import domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
	@Query("select r from Review r where r.referee.id=?1")
	Collection<Review> getReviewReferee(int refereeId);

}
