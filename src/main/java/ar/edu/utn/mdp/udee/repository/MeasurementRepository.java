package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

@Repository
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

    Page<Measurement> findByUserId(Integer id, Pageable pageable);

    Page<Measurement> findByMeasureDateTimeAfter(LocalDateTime sinceMeasureDateTime, Pageable pageable);

    Page<Measurement> findByMeasureDateTimeBefore(LocalDateTime untilDateTime, Pageable pageable);

    Page<Measurement> findByMeasureDateTimeBetween(LocalDateTime sinceMeasureDateTime, LocalDateTime untilMeasureDateTime, Pageable pageable);
}
