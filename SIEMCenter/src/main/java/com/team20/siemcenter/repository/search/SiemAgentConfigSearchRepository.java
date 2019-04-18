package com.team20.siemcenter.repository.search;

import com.team20.siemcenter.domain.SiemAgentConfig;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SiemAgentConfig entity.
 */
public interface SiemAgentConfigSearchRepository extends ElasticsearchRepository<SiemAgentConfig, Long> {
}
