
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import domain.LegalRecord;
import domain.PeriodRecord;

/*
 * CONTROL DE CAMBIOS PeriodRecoredRepository.java
 * 
 * ALVARO 09/02/2019 23:39 CREACIÓN DE LA CLASE
 */

@Repository
public interface PeriodRecordRepository extends JpaRepository<PeriodRecord, Integer> {

}
