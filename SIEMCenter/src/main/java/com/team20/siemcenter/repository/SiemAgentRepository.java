package com.team20.siemcenter.repository;

import com.team20.siemcenter.domain.SiemAgent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SiemAgent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiemAgentRepository extends JpaRepository<SiemAgent, Long> {

}
