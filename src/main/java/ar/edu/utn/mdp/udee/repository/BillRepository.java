package ar.edu.utn.mdp.udee.repository;

import ar.edu.utn.mdp.udee.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

    @Query(
            value =
                "SELECT B FROM Bill B " +
                "WHERE B.billDate BETWEEN ?1 AND ?2 "
    )
    Page<Bill> findByRange(Date since, Date until, Pageable pageable);

}
