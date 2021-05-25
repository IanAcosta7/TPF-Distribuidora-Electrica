package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByUsernameAndPassword(String username, String password);
    @Modifying
    @Query("update User set user_type_id = ?2 where id = ?1")
    @Transactional
    Integer setUserType(Integer id, Integer typeId);
}
