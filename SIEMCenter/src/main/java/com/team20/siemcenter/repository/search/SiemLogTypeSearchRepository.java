package com.team20.siemcenter.repository.search;

import com.team20.siemcenter.domain.SiemLogType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SiemLogType entity.
 */
public interface SiemLogTypeSearchRepository extends ElasticsearchRepository<SiemLogType, Long> {
}
