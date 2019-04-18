package com.team20.siemcenter.repository.search;

import com.team20.siemcenter.domain.SiemAgent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SiemAgent entity.
 */
public interface SiemAgentSearchRepository extends ElasticsearchRepository<SiemAgent, Long> {
}
