package com.sbnz.SIEMCenter2.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbnz.SIEMCenter2.model.AlarmTriggered;


@Repository
public interface AlarmTriggeredRepository extends JpaRepository<AlarmTriggered, Long>{

	List<AlarmTriggered> findByType(int type);

	
}
