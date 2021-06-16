package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {

    @Query(
            value =
                "SELECT T FROM Tariff T " +
                "INNER JOIN Address A ON A.tariff.id = T.id " +
                "WHERE A.electricMeter.id = ?1"
    )
    Tariff findTariffByElectricMeter(Integer electricMeterId);

}
