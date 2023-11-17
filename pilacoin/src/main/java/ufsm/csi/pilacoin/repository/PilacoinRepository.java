package ufsm.csi.pilacoin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufsm.csi.pilacoin.model.PilaCoin;

@Repository
public interface PilacoinRepository extends JpaRepository<PilaCoin, Long> {

}
