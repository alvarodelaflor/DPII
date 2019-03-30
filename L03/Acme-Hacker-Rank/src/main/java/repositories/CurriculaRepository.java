package repositories;

import java.util.Collection;

/*
 * CurriculaRepository.java
 * 
 * author: Álvaro de la Flor Bonilla GitHub: alvar017
 * 
 * CONTROL:
 * 30/03/2019 14:00 Creation
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {
	
	@Query("select c from Curricula c where c.hacker.id=?1")
	public Collection<Curricula> getCurriculaOfHacker(int hackerId);
}
