package com.team20.siemcenter.repository.search;

import com.team20.siemcenter.domain.Alarm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Alarm entity.
 */
public interface AlarmSearchRepository extends ElasticsearchRepository<Alarm, Long> {
}
