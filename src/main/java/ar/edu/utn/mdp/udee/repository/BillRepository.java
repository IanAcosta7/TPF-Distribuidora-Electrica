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

    @Query(
            value =
                "SELECT B FROM Bill B " +
                "WHERE B.amountPayed < B.total " +
                "AND B.address.id = ?1"
    )
    Page<Bill> findUnpaidByAddress(Integer addressId, Pageable pageable);

    @Query(
            value =
                "SELECT B FROM Bill B " +
                "INNER JOIN Address A ON A.id = B.address.id " +
                "WHERE B.amountPayed < B.total " +
                "AND A.client.id = ?1"
    )
    Page<Bill> findUnpaidByClient(Integer clientId, Pageable pageable);

}
