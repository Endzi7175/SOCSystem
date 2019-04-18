package com.team20.siemcenter.repository;

import com.team20.siemcenter.domain.SiemLogType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SiemLogType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiemLogTypeRepository extends JpaRepository<SiemLogType, Long> {

}
