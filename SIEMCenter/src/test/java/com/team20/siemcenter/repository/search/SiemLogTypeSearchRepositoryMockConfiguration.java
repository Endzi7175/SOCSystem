package com.team20.siemcenter.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of SiemLogTypeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SiemLogTypeSearchRepositoryMockConfiguration {

    @MockBean
    private SiemLogTypeSearchRepository mockSiemLogTypeSearchRepository;

}
