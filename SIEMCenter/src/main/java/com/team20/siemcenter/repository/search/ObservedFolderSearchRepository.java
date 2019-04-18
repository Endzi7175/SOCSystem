package com.team20.siemcenter.repository.search;

import com.team20.siemcenter.domain.ObservedFolder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ObservedFolder entity.
 */
public interface ObservedFolderSearchRepository extends ElasticsearchRepository<ObservedFolder, Long> {
}
