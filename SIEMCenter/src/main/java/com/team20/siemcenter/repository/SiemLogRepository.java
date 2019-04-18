package com.team20.siemcenter.repository;

import com.team20.siemcenter.domain.SiemLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SiemLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiemLogRepository extends JpaRepository<SiemLog, Long> {

}
