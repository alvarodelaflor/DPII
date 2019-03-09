
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import domain.LegalRecord;

/*
 * CONTROL DE CAMBIOS ProcessionRepository.java
 * 
 * ALVARO 17/02/2019 11:43 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 12:17 AÑADIDA QUERY findProcessionsByBrotherhood
 */

@Repository
public interface LegalRecordRepository extends JpaRepository<LegalRecord, Integer> {

}
