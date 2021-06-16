package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.ElectricMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricMeterRepository extends JpaRepository<ElectricMeter, Integer> {
    ElectricMeter findBySerialNumberAndPassword(String serialNumber, String password);
}
