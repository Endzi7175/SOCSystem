package com.team20.siemcenter.repository.search;

import com.team20.siemcenter.domain.SiemLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SiemLog entity.
 */
public interface SiemLogSearchRepository extends ElasticsearchRepository<SiemLog, Long> {
}
