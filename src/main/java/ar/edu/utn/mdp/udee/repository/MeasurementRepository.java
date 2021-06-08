package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    @Query(value =
            "SELECT M.* FROM measurements M INNER JOIN electric_meters EM ON M.electric_meter_id = EM.id INNER JOIN addresses A ON EM.id = A.electric_meter_id WHERE A.client_id = ?1 ORDER BY ?#{#pageable}",
            countQuery =
            "SELECT COUNT(M.*) FROM measurements M" +
            "INNER JOIN electric_meters EM ON M.electric_meter_id = EM.id" +
            "INNER JOIN addresses A ON EM.id = A.electric_meter_id" +
            "WHERE A.client_id = ?1",
            nativeQuery = true
    )
    public Page<Measurement> findByUserId(Integer id, Pageable pageable);

}
