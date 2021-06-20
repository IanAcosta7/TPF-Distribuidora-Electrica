package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.ElectricMeter;
import ar.edu.utn.mdp.udee.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    @Query(
            value =
                "SELECT M.* FROM measurements M " +
                "INNER JOIN electric_meters EM ON M.electric_meter_id = EM.id " +
                "INNER JOIN addresses A ON EM.id = A.electric_meter_id " +
                "WHERE A.client_id = ?1 " +
                "ORDER BY ?#{#pageable}",
            countQuery =
                "SELECT COUNT(M.*) FROM measurements M" +
                "INNER JOIN electric_meters EM ON M.electric_meter_id = EM.id" +
                "INNER JOIN addresses A ON EM.id = A.electric_meter_id" +
                "WHERE A.client_id = ?1",
            nativeQuery = true
    )
    Page<Measurement> findByUserId(Integer id, Pageable pageable);

    @Query(
            value =
                "SELECT M FROM Measurement M " +
                "WHERE M.measureDateTime BETWEEN ?1 AND ?2 "
    )
    Page<Measurement> findRange(LocalDateTime since, LocalDateTime until, Pageable pageable);

    @Query(
            value =
                "SELECT M FROM Measurement M " +
                "INNER JOIN ElectricMeter EM ON M.electricMeter.id = EM.id " +
                "INNER JOIN Address A ON EM.id = A.electricMeter.id " +
                "WHERE M.measureDateTime BETWEEN ?1 AND ?2 " +
                "AND A.client.id = ?3 "
    )
    Page<Measurement> findRangeFromUser(LocalDateTime since, LocalDateTime until, Integer userId, Pageable pageable);

    @Query(
            value =
                "SELECT M FROM Measurement M " +
                "INNER JOIN ElectricMeter EM ON M.electricMeter.id = EM.id " +
                "INNER JOIN Address A ON EM.id = A.electricMeter.id " +
                "WHERE M.measureDateTime BETWEEN ?1 AND ?2 " +
                "AND A.id = ?3 "
    )
    Page<Measurement> findRangeFromAddress(LocalDateTime since, LocalDateTime until, Integer addressId, Pageable pageable);

    @Query(
            value =
                "SELECT M.* FROM measurements M " +
                "WHERE M.electric_meter_id = ?1 " +
                "ORDER BY M.measure DESC, M.measure_date_time DESC, M.id DESC " +
                "LIMIT 1",
            nativeQuery = true
    )
    Measurement getTopByElectricMeter(Integer electricMeterId);

}
