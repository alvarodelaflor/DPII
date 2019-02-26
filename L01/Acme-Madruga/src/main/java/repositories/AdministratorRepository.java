
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

/*
 * CONTROL DE CAMBIOS AdminRepository.java
 * FRAN 19/02/2019 11:10 CREACIÓN DE LA CLASE
 */

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a join a.userAccount aacc where aacc.id=?1")
	Administrator findByUserAccountId(int userAccountId);
}
