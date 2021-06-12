package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.User;
import ar.edu.utn.mdp.udee.model.projection.UserConsumptionProjection;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    @Modifying
    @Query("update User set user_type_id = ?2 where id = ?1")
    @Transactional
    Integer setUserType(Integer id, Integer typeId);

    @Query(value = "CALL sp_get_top_consumers(?1, ?2);", nativeQuery = true)
    List<UserConsumptionProjection> getTopConsumers(LocalDateTime sinceMeasureDateTime, LocalDateTime untilMeasureDateTime);
}
