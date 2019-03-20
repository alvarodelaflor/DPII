
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	/*
	 * CONTROL DE CAMBIOS AreaRepository.java
	 * CARMEN 20/02/2019 17:31 CREACIÓN findAreaChapter
	 */

	@Query("select a from Area a where a.chapter.id=?1")
	Area findAreaChapter(int id);

	// REQUISITO 8
	@Query("select count(a) from Area a where a.chapter = null")
	Integer AreaNoChapter();

	// REQUISITO 8
	@Query("select count(a1) from Area a1")
	Integer AreaALL();

}
