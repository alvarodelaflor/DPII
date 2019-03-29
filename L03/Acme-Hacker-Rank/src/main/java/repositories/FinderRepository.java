
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Finder;

public interface FinderRepository extends JpaRepository<Finder, Integer> {

}
