package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
}
