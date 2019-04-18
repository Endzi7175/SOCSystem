package com.team20.siemcenter.web.rest;

import com.team20.siemcenter.SiemcenterApp;

import com.team20.siemcenter.domain.SiemAgentConfig;
import com.team20.siemcenter.repository.SiemAgentConfigRepository;
import com.team20.siemcenter.repository.search.SiemAgentConfigSearchRepository;
import com.team20.siemcenter.service.SiemAgentConfigService;
import com.team20.siemcenter.service.dto.SiemAgentConfigDTO;
import com.team20.siemcenter.service.mapper.SiemAgentConfigMapper;
import com.team20.siemcenter.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.team20.siemcenter.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SiemAgentConfigResource REST controller.
 *
 * @see SiemAgentConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiemcenterApp.class)
public class SiemAgentConfigResourceIntTest {

    private static final String DEFAULT_LOG_DESTINATION_IP = "AAAAAAAAAA";
    private static final String UPDATED_LOG_DESTINATION_IP = "BBBBBBBBBB";

    @Autowired
    private SiemAgentConfigRepository siemAgentConfigRepository;

    @Autowired
    private SiemAgentConfigMapper siemAgentConfigMapper;

    @Autowired
    private SiemAgentConfigService siemAgentConfigService;

    /**
     * This repository is mocked in the com.team20.siemcenter.repository.search test package.
     *
     * @see com.team20.siemcenter.repository.search.SiemAgentConfigSearchRepositoryMockConfiguration
     */
    @Autowired
    private SiemAgentConfigSearchRepository mockSiemAgentConfigSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSiemAgentConfigMockMvc;

    private SiemAgentConfig siemAgentConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SiemAgentConfigResource siemAgentConfigResource = new SiemAgentConfigResource(siemAgentConfigService);
        this.restSiemAgentConfigMockMvc = MockMvcBuilders.standaloneSetup(siemAgentConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiemAgentConfig createEntity(EntityManager em) {
        SiemAgentConfig siemAgentConfig = new SiemAgentConfig()
            .logDestinationIp(DEFAULT_LOG_DESTINATION_IP);
        return siemAgentConfig;
    }

    @Before
    public void initTest() {
        siemAgentConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiemAgentConfig() throws Exception {
        int databaseSizeBeforeCreate = siemAgentConfigRepository.findAll().size();

        // Create the SiemAgentConfig
        SiemAgentConfigDTO siemAgentConfigDTO = siemAgentConfigMapper.toDto(siemAgentConfig);
        restSiemAgentConfigMockMvc.perform(post("/api/siem-agent-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemAgentConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the SiemAgentConfig in the database
        List<SiemAgentConfig> siemAgentConfigList = siemAgentConfigRepository.findAll();
        assertThat(siemAgentConfigList).hasSize(databaseSizeBeforeCreate + 1);
        SiemAgentConfig testSiemAgentConfig = siemAgentConfigList.get(siemAgentConfigList.size() - 1);
        assertThat(testSiemAgentConfig.getLogDestinationIp()).isEqualTo(DEFAULT_LOG_DESTINATION_IP);

        // Validate the SiemAgentConfig in Elasticsearch
        verify(mockSiemAgentConfigSearchRepository, times(1)).save(testSiemAgentConfig);
    }

    @Test
    @Transactional
    public void createSiemAgentConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siemAgentConfigRepository.findAll().size();

        // Create the SiemAgentConfig with an existing ID
        siemAgentConfig.setId(1L);
        SiemAgentConfigDTO siemAgentConfigDTO = siemAgentConfigMapper.toDto(siemAgentConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiemAgentConfigMockMvc.perform(post("/api/siem-agent-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemAgentConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiemAgentConfig in the database
        List<SiemAgentConfig> siemAgentConfigList = siemAgentConfigRepository.findAll();
        assertThat(siemAgentConfigList).hasSize(databaseSizeBeforeCreate);

        // Validate the SiemAgentConfig in Elasticsearch
        verify(mockSiemAgentConfigSearchRepository, times(0)).save(siemAgentConfig);
    }

    @Test
    @Transactional
    public void getAllSiemAgentConfigs() throws Exception {
        // Initialize the database
        siemAgentConfigRepository.saveAndFlush(siemAgentConfig);

        // Get all the siemAgentConfigList
        restSiemAgentConfigMockMvc.perform(get("/api/siem-agent-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siemAgentConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].logDestinationIp").value(hasItem(DEFAULT_LOG_DESTINATION_IP.toString())));
    }
    
    @Test
    @Transactional
    public void getSiemAgentConfig() throws Exception {
        // Initialize the database
        siemAgentConfigRepository.saveAndFlush(siemAgentConfig);

        // Get the siemAgentConfig
        restSiemAgentConfigMockMvc.perform(get("/api/siem-agent-configs/{id}", siemAgentConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(siemAgentConfig.getId().intValue()))
            .andExpect(jsonPath("$.logDestinationIp").value(DEFAULT_LOG_DESTINATION_IP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSiemAgentConfig() throws Exception {
        // Get the siemAgentConfig
        restSiemAgentConfigMockMvc.perform(get("/api/siem-agent-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiemAgentConfig() throws Exception {
        // Initialize the database
        siemAgentConfigRepository.saveAndFlush(siemAgentConfig);

        int databaseSizeBeforeUpdate = siemAgentConfigRepository.findAll().size();

        // Update the siemAgentConfig
        SiemAgentConfig updatedSiemAgentConfig = siemAgentConfigRepository.findById(siemAgentConfig.getId()).get();
        // Disconnect from session so that the updates on updatedSiemAgentConfig are not directly saved in db
        em.detach(updatedSiemAgentConfig);
        updatedSiemAgentConfig
            .logDestinationIp(UPDATED_LOG_DESTINATION_IP);
        SiemAgentConfigDTO siemAgentConfigDTO = siemAgentConfigMapper.toDto(updatedSiemAgentConfig);

        restSiemAgentConfigMockMvc.perform(put("/api/siem-agent-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemAgentConfigDTO)))
            .andExpect(status().isOk());

        // Validate the SiemAgentConfig in the database
        List<SiemAgentConfig> siemAgentConfigList = siemAgentConfigRepository.findAll();
        assertThat(siemAgentConfigList).hasSize(databaseSizeBeforeUpdate);
        SiemAgentConfig testSiemAgentConfig = siemAgentConfigList.get(siemAgentConfigList.size() - 1);
        assertThat(testSiemAgentConfig.getLogDestinationIp()).isEqualTo(UPDATED_LOG_DESTINATION_IP);

        // Validate the SiemAgentConfig in Elasticsearch
        verify(mockSiemAgentConfigSearchRepository, times(1)).save(testSiemAgentConfig);
    }

    @Test
    @Transactional
    public void updateNonExistingSiemAgentConfig() throws Exception {
        int databaseSizeBeforeUpdate = siemAgentConfigRepository.findAll().size();

        // Create the SiemAgentConfig
        SiemAgentConfigDTO siemAgentConfigDTO = siemAgentConfigMapper.toDto(siemAgentConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiemAgentConfigMockMvc.perform(put("/api/siem-agent-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemAgentConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiemAgentConfig in the database
        List<SiemAgentConfig> siemAgentConfigList = siemAgentConfigRepository.findAll();
        assertThat(siemAgentConfigList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SiemAgentConfig in Elasticsearch
        verify(mockSiemAgentConfigSearchRepository, times(0)).save(siemAgentConfig);
    }

    @Test
    @Transactional
    public void deleteSiemAgentConfig() throws Exception {
        // Initialize the database
        siemAgentConfigRepository.saveAndFlush(siemAgentConfig);

        int databaseSizeBeforeDelete = siemAgentConfigRepository.findAll().size();

        // Delete the siemAgentConfig
        restSiemAgentConfigMockMvc.perform(delete("/api/siem-agent-configs/{id}", siemAgentConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SiemAgentConfig> siemAgentConfigList = siemAgentConfigRepository.findAll();
        assertThat(siemAgentConfigList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SiemAgentConfig in Elasticsearch
        verify(mockSiemAgentConfigSearchRepository, times(1)).deleteById(siemAgentConfig.getId());
    }

    @Test
    @Transactional
    public void searchSiemAgentConfig() throws Exception {
        // Initialize the database
        siemAgentConfigRepository.saveAndFlush(siemAgentConfig);
        when(mockSiemAgentConfigSearchRepository.search(queryStringQuery("id:" + siemAgentConfig.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(siemAgentConfig), PageRequest.of(0, 1), 1));
        // Search the siemAgentConfig
        restSiemAgentConfigMockMvc.perform(get("/api/_search/siem-agent-configs?query=id:" + siemAgentConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siemAgentConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].logDestinationIp").value(hasItem(DEFAULT_LOG_DESTINATION_IP)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiemAgentConfig.class);
        SiemAgentConfig siemAgentConfig1 = new SiemAgentConfig();
        siemAgentConfig1.setId(1L);
        SiemAgentConfig siemAgentConfig2 = new SiemAgentConfig();
        siemAgentConfig2.setId(siemAgentConfig1.getId());
        assertThat(siemAgentConfig1).isEqualTo(siemAgentConfig2);
        siemAgentConfig2.setId(2L);
        assertThat(siemAgentConfig1).isNotEqualTo(siemAgentConfig2);
        siemAgentConfig1.setId(null);
        assertThat(siemAgentConfig1).isNotEqualTo(siemAgentConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiemAgentConfigDTO.class);
        SiemAgentConfigDTO siemAgentConfigDTO1 = new SiemAgentConfigDTO();
        siemAgentConfigDTO1.setId(1L);
        SiemAgentConfigDTO siemAgentConfigDTO2 = new SiemAgentConfigDTO();
        assertThat(siemAgentConfigDTO1).isNotEqualTo(siemAgentConfigDTO2);
        siemAgentConfigDTO2.setId(siemAgentConfigDTO1.getId());
        assertThat(siemAgentConfigDTO1).isEqualTo(siemAgentConfigDTO2);
        siemAgentConfigDTO2.setId(2L);
        assertThat(siemAgentConfigDTO1).isNotEqualTo(siemAgentConfigDTO2);
        siemAgentConfigDTO1.setId(null);
        assertThat(siemAgentConfigDTO1).isNotEqualTo(siemAgentConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(siemAgentConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(siemAgentConfigMapper.fromId(null)).isNull();
    }
}
