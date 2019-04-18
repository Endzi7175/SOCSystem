package com.team20.siemcenter.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AlarmDefinitionSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AlarmDefinitionSearchRepositoryMockConfiguration {

    @MockBean
    private AlarmDefinitionSearchRepository mockAlarmDefinitionSearchRepository;

}
