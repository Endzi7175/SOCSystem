package com.team20.siemcenter.repository;

import com.team20.siemcenter.domain.Alarm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Alarm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
