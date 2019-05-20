package com.sbnz.SIEMCenter2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbnz.SIEMCenter2.model.LogEntry;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
	
}
