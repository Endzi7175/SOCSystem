package com.team20.siemcenter.repository;

import com.team20.siemcenter.domain.SiemAgentConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SiemAgentConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiemAgentConfigRepository extends JpaRepository<SiemAgentConfig, Long> {

}
