package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.TariffType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffTypeRepository extends JpaRepository<TariffType, Integer> {
}
