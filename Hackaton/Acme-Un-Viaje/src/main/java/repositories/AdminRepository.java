
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select b from Admin b join b.userAccount bua where bua.id=?1")
	Admin findByUserAccountId(int userAccountId);
}
