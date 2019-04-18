package com.team20.siemcenter.repository.search;

import com.team20.siemcenter.domain.AlarmDefinition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AlarmDefinition entity.
 */
public interface AlarmDefinitionSearchRepository extends ElasticsearchRepository<AlarmDefinition, Long> {
}
