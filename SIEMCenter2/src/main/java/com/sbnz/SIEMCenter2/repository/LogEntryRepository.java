package com.sbnz.SIEMCenter2.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sbnz.SIEMCenter2.model.LogEntry;

public interface LogEntryRepository extends ElasticsearchRepository<LogEntry, String> {
	
}
