package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User getUserByUsernameAndPassword(String username, String password);
}
