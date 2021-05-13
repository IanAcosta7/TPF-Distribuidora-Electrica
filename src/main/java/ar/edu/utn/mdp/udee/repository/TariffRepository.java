package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Integer> {
}
