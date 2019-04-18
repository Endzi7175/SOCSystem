package com.team20.siemcenter.web.rest;

import com.team20.siemcenter.SiemcenterApp;

import com.team20.siemcenter.domain.SiemAgent;
import com.team20.siemcenter.repository.SiemAgentRepository;
import com.team20.siemcenter.repository.search.SiemAgentSearchRepository;
import com.team20.siemcenter.service.SiemAgentService;
import com.team20.siemcenter.service.dto.SiemAgentDTO;
import com.team20.siemcenter.service.mapper.SiemAgentMapper;
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
 * Test class for the SiemAgentResource REST controller.
 *
 * @see SiemAgentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiemcenterApp.class)
public class SiemAgentResourceIntTest {

    private static final String DEFAULT_CN = "AAAAAAAAAA";
    private static final String UPDATED_CN = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLIC_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private SiemAgentRepository siemAgentRepository;

    @Autowired
    private SiemAgentMapper siemAgentMapper;

    @Autowired
    private SiemAgentService siemAgentService;

    /**
     * This repository is mocked in the com.team20.siemcenter.repository.search test package.
     *
     * @see com.team20.siemcenter.repository.search.SiemAgentSearchRepositoryMockConfiguration
     */
    @Autowired
    private SiemAgentSearchRepository mockSiemAgentSearchRepository;

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

    private MockMvc restSiemAgentMockMvc;

    private SiemAgent siemAgent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SiemAgentResource siemAgentResource = new SiemAgentResource(siemAgentService);
        this.restSiemAgentMockMvc = MockMvcBuilders.standaloneSetup(siemAgentResource)
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
    public static SiemAgent createEntity(EntityManager em) {
        SiemAgent siemAgent = new SiemAgent()
            .cn(DEFAULT_CN)
            .publicKey(DEFAULT_PUBLIC_KEY)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .active(DEFAULT_ACTIVE);
        return siemAgent;
    }

    @Before
    public void initTest() {
        siemAgent = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiemAgent() throws Exception {
        int databaseSizeBeforeCreate = siemAgentRepository.findAll().size();

        // Create the SiemAgent
        SiemAgentDTO siemAgentDTO = siemAgentMapper.toDto(siemAgent);
        restSiemAgentMockMvc.perform(post("/api/siem-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemAgentDTO)))
            .andExpect(status().isCreated());

        // Validate the SiemAgent in the database
        List<SiemAgent> siemAgentList = siemAgentRepository.findAll();
        assertThat(siemAgentList).hasSize(databaseSizeBeforeCreate + 1);
        SiemAgent testSiemAgent = siemAgentList.get(siemAgentList.size() - 1);
        assertThat(testSiemAgent.getCn()).isEqualTo(DEFAULT_CN);
        assertThat(testSiemAgent.getPublicKey()).isEqualTo(DEFAULT_PUBLIC_KEY);
        assertThat(testSiemAgent.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testSiemAgent.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the SiemAgent in Elasticsearch
        verify(mockSiemAgentSearchRepository, times(1)).save(testSiemAgent);
    }

    @Test
    @Transactional
    public void createSiemAgentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siemAgentRepository.findAll().size();

        // Create the SiemAgent with an existing ID
        siemAgent.setId(1L);
        SiemAgentDTO siemAgentDTO = siemAgentMapper.toDto(siemAgent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiemAgentMockMvc.perform(post("/api/siem-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemAgentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiemAgent in the database
        List<SiemAgent> siemAgentList = siemAgentRepository.findAll();
        assertThat(siemAgentList).hasSize(databaseSizeBeforeCreate);

        // Validate the SiemAgent in Elasticsearch
        verify(mockSiemAgentSearchRepository, times(0)).save(siemAgent);
    }

    @Test
    @Transactional
    public void getAllSiemAgents() throws Exception {
        // Initialize the database
        siemAgentRepository.saveAndFlush(siemAgent);

        // Get all the siemAgentList
        restSiemAgentMockMvc.perform(get("/api/siem-agents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siemAgent.getId().intValue())))
            .andExpect(jsonPath("$.[*].cn").value(hasItem(DEFAULT_CN.toString())))
            .andExpect(jsonPath("$.[*].publicKey").value(hasItem(DEFAULT_PUBLIC_KEY.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSiemAgent() throws Exception {
        // Initialize the database
        siemAgentRepository.saveAndFlush(siemAgent);

        // Get the siemAgent
        restSiemAgentMockMvc.perform(get("/api/siem-agents/{id}", siemAgent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(siemAgent.getId().intValue()))
            .andExpect(jsonPath("$.cn").value(DEFAULT_CN.toString()))
            .andExpect(jsonPath("$.publicKey").value(DEFAULT_PUBLIC_KEY.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSiemAgent() throws Exception {
        // Get the siemAgent
        restSiemAgentMockMvc.perform(get("/api/siem-agents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiemAgent() throws Exception {
        // Initialize the database
        siemAgentRepository.saveAndFlush(siemAgent);

        int databaseSizeBeforeUpdate = siemAgentRepository.findAll().size();

        // Update the siemAgent
        SiemAgent updatedSiemAgent = siemAgentRepository.findById(siemAgent.getId()).get();
        // Disconnect from session so that the updates on updatedSiemAgent are not directly saved in db
        em.detach(updatedSiemAgent);
        updatedSiemAgent
            .cn(UPDATED_CN)
            .publicKey(UPDATED_PUBLIC_KEY)
            .ipAddress(UPDATED_IP_ADDRESS)
            .active(UPDATED_ACTIVE);
        SiemAgentDTO siemAgentDTO = siemAgentMapper.toDto(updatedSiemAgent);

        restSiemAgentMockMvc.perform(put("/api/siem-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemAgentDTO)))
            .andExpect(status().isOk());

        // Validate the SiemAgent in the database
        List<SiemAgent> siemAgentList = siemAgentRepository.findAll();
        assertThat(siemAgentList).hasSize(databaseSizeBeforeUpdate);
        SiemAgent testSiemAgent = siemAgentList.get(siemAgentList.size() - 1);
        assertThat(testSiemAgent.getCn()).isEqualTo(UPDATED_CN);
        assertThat(testSiemAgent.getPublicKey()).isEqualTo(UPDATED_PUBLIC_KEY);
        assertThat(testSiemAgent.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testSiemAgent.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the SiemAgent in Elasticsearch
        verify(mockSiemAgentSearchRepository, times(1)).save(testSiemAgent);
    }

    @Test
    @Transactional
    public void updateNonExistingSiemAgent() throws Exception {
        int databaseSizeBeforeUpdate = siemAgentRepository.findAll().size();

        // Create the SiemAgent
        SiemAgentDTO siemAgentDTO = siemAgentMapper.toDto(siemAgent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiemAgentMockMvc.perform(put("/api/siem-agents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemAgentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiemAgent in the database
        List<SiemAgent> siemAgentList = siemAgentRepository.findAll();
        assertThat(siemAgentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SiemAgent in Elasticsearch
        verify(mockSiemAgentSearchRepository, times(0)).save(siemAgent);
    }

    @Test
    @Transactional
    public void deleteSiemAgent() throws Exception {
        // Initialize the database
        siemAgentRepository.saveAndFlush(siemAgent);

        int databaseSizeBeforeDelete = siemAgentRepository.findAll().size();

        // Delete the siemAgent
        restSiemAgentMockMvc.perform(delete("/api/siem-agents/{id}", siemAgent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SiemAgent> siemAgentList = siemAgentRepository.findAll();
        assertThat(siemAgentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SiemAgent in Elasticsearch
        verify(mockSiemAgentSearchRepository, times(1)).deleteById(siemAgent.getId());
    }

    @Test
    @Transactional
    public void searchSiemAgent() throws Exception {
        // Initialize the database
        siemAgentRepository.saveAndFlush(siemAgent);
        when(mockSiemAgentSearchRepository.search(queryStringQuery("id:" + siemAgent.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(siemAgent), PageRequest.of(0, 1), 1));
        // Search the siemAgent
        restSiemAgentMockMvc.perform(get("/api/_search/siem-agents?query=id:" + siemAgent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siemAgent.getId().intValue())))
            .andExpect(jsonPath("$.[*].cn").value(hasItem(DEFAULT_CN)))
            .andExpect(jsonPath("$.[*].publicKey").value(hasItem(DEFAULT_PUBLIC_KEY)))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiemAgent.class);
        SiemAgent siemAgent1 = new SiemAgent();
        siemAgent1.setId(1L);
        SiemAgent siemAgent2 = new SiemAgent();
        siemAgent2.setId(siemAgent1.getId());
        assertThat(siemAgent1).isEqualTo(siemAgent2);
        siemAgent2.setId(2L);
        assertThat(siemAgent1).isNotEqualTo(siemAgent2);
        siemAgent1.setId(null);
        assertThat(siemAgent1).isNotEqualTo(siemAgent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiemAgentDTO.class);
        SiemAgentDTO siemAgentDTO1 = new SiemAgentDTO();
        siemAgentDTO1.setId(1L);
        SiemAgentDTO siemAgentDTO2 = new SiemAgentDTO();
        assertThat(siemAgentDTO1).isNotEqualTo(siemAgentDTO2);
        siemAgentDTO2.setId(siemAgentDTO1.getId());
        assertThat(siemAgentDTO1).isEqualTo(siemAgentDTO2);
        siemAgentDTO2.setId(2L);
        assertThat(siemAgentDTO1).isNotEqualTo(siemAgentDTO2);
        siemAgentDTO1.setId(null);
        assertThat(siemAgentDTO1).isNotEqualTo(siemAgentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(siemAgentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(siemAgentMapper.fromId(null)).isNull();
    }
}
