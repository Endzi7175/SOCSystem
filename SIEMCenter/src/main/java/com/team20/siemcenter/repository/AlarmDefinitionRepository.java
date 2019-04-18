package com.team20.siemcenter.repository;

import com.team20.siemcenter.domain.AlarmDefinition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AlarmDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmDefinitionRepository extends JpaRepository<AlarmDefinition, Long> {

}
