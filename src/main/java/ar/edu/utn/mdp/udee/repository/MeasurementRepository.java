package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
