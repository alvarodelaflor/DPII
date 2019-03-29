
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Hacker;

public interface HackerRepository extends JpaRepository<Hacker, Integer> {

}
