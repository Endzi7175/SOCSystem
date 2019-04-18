package com.team20.siemcenter.web.rest;

import com.team20.siemcenter.SiemcenterApp;

import com.team20.siemcenter.domain.SiemLogType;
import com.team20.siemcenter.repository.SiemLogTypeRepository;
import com.team20.siemcenter.repository.search.SiemLogTypeSearchRepository;
import com.team20.siemcenter.service.SiemLogTypeService;
import com.team20.siemcenter.service.dto.SiemLogTypeDTO;
import com.team20.siemcenter.service.mapper.SiemLogTypeMapper;
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

import com.team20.siemcenter.domain.enumeration.OperatingSystem;
/**
 * Test class for the SiemLogTypeResource REST controller.
 *
 * @see SiemLogTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiemcenterApp.class)
public class SiemLogTypeResourceIntTest {

    private static final String DEFAULT_TIMESTAMP_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_TIMESTAMP_REGEX = "BBBBBBBBBB";

    private static final String DEFAULT_SEVERITY_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_SEVERITY_REGEX = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT_REGEX = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_REGEX = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_TIME_FORMAT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_TIME_FORMAT = "BBBBBBBBBB";

    private static final OperatingSystem DEFAULT_OPERATING_SYSTEM = OperatingSystem.WINDOWS;
    private static final OperatingSystem UPDATED_OPERATING_SYSTEM = OperatingSystem.LINUX;

    @Autowired
    private SiemLogTypeRepository siemLogTypeRepository;

    @Autowired
    private SiemLogTypeMapper siemLogTypeMapper;

    @Autowired
    private SiemLogTypeService siemLogTypeService;

    /**
     * This repository is mocked in the com.team20.siemcenter.repository.search test package.
     *
     * @see com.team20.siemcenter.repository.search.SiemLogTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private SiemLogTypeSearchRepository mockSiemLogTypeSearchRepository;

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

    private MockMvc restSiemLogTypeMockMvc;

    private SiemLogType siemLogType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SiemLogTypeResource siemLogTypeResource = new SiemLogTypeResource(siemLogTypeService);
        this.restSiemLogTypeMockMvc = MockMvcBuilders.standaloneSetup(siemLogTypeResource)
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
    public static SiemLogType createEntity(EntityManager em) {
        SiemLogType siemLogType = new SiemLogType()
            .timestampRegex(DEFAULT_TIMESTAMP_REGEX)
            .severityRegex(DEFAULT_SEVERITY_REGEX)
            .contextRegex(DEFAULT_CONTEXT_REGEX)
            .messageRegex(DEFAULT_MESSAGE_REGEX)
            .name(DEFAULT_NAME)
            .dateTimeFormat(DEFAULT_DATE_TIME_FORMAT)
            .operatingSystem(DEFAULT_OPERATING_SYSTEM);
        return siemLogType;
    }

    @Before
    public void initTest() {
        siemLogType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiemLogType() throws Exception {
        int databaseSizeBeforeCreate = siemLogTypeRepository.findAll().size();

        // Create the SiemLogType
        SiemLogTypeDTO siemLogTypeDTO = siemLogTypeMapper.toDto(siemLogType);
        restSiemLogTypeMockMvc.perform(post("/api/siem-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemLogTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the SiemLogType in the database
        List<SiemLogType> siemLogTypeList = siemLogTypeRepository.findAll();
        assertThat(siemLogTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SiemLogType testSiemLogType = siemLogTypeList.get(siemLogTypeList.size() - 1);
        assertThat(testSiemLogType.getTimestampRegex()).isEqualTo(DEFAULT_TIMESTAMP_REGEX);
        assertThat(testSiemLogType.getSeverityRegex()).isEqualTo(DEFAULT_SEVERITY_REGEX);
        assertThat(testSiemLogType.getContextRegex()).isEqualTo(DEFAULT_CONTEXT_REGEX);
        assertThat(testSiemLogType.getMessageRegex()).isEqualTo(DEFAULT_MESSAGE_REGEX);
        assertThat(testSiemLogType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSiemLogType.getDateTimeFormat()).isEqualTo(DEFAULT_DATE_TIME_FORMAT);
        assertThat(testSiemLogType.getOperatingSystem()).isEqualTo(DEFAULT_OPERATING_SYSTEM);

        // Validate the SiemLogType in Elasticsearch
        verify(mockSiemLogTypeSearchRepository, times(1)).save(testSiemLogType);
    }

    @Test
    @Transactional
    public void createSiemLogTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siemLogTypeRepository.findAll().size();

        // Create the SiemLogType with an existing ID
        siemLogType.setId(1L);
        SiemLogTypeDTO siemLogTypeDTO = siemLogTypeMapper.toDto(siemLogType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiemLogTypeMockMvc.perform(post("/api/siem-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemLogTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiemLogType in the database
        List<SiemLogType> siemLogTypeList = siemLogTypeRepository.findAll();
        assertThat(siemLogTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the SiemLogType in Elasticsearch
        verify(mockSiemLogTypeSearchRepository, times(0)).save(siemLogType);
    }

    @Test
    @Transactional
    public void getAllSiemLogTypes() throws Exception {
        // Initialize the database
        siemLogTypeRepository.saveAndFlush(siemLogType);

        // Get all the siemLogTypeList
        restSiemLogTypeMockMvc.perform(get("/api/siem-log-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siemLogType.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestampRegex").value(hasItem(DEFAULT_TIMESTAMP_REGEX.toString())))
            .andExpect(jsonPath("$.[*].severityRegex").value(hasItem(DEFAULT_SEVERITY_REGEX.toString())))
            .andExpect(jsonPath("$.[*].contextRegex").value(hasItem(DEFAULT_CONTEXT_REGEX.toString())))
            .andExpect(jsonPath("$.[*].messageRegex").value(hasItem(DEFAULT_MESSAGE_REGEX.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateTimeFormat").value(hasItem(DEFAULT_DATE_TIME_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].operatingSystem").value(hasItem(DEFAULT_OPERATING_SYSTEM.toString())));
    }
    
    @Test
    @Transactional
    public void getSiemLogType() throws Exception {
        // Initialize the database
        siemLogTypeRepository.saveAndFlush(siemLogType);

        // Get the siemLogType
        restSiemLogTypeMockMvc.perform(get("/api/siem-log-types/{id}", siemLogType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(siemLogType.getId().intValue()))
            .andExpect(jsonPath("$.timestampRegex").value(DEFAULT_TIMESTAMP_REGEX.toString()))
            .andExpect(jsonPath("$.severityRegex").value(DEFAULT_SEVERITY_REGEX.toString()))
            .andExpect(jsonPath("$.contextRegex").value(DEFAULT_CONTEXT_REGEX.toString()))
            .andExpect(jsonPath("$.messageRegex").value(DEFAULT_MESSAGE_REGEX.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateTimeFormat").value(DEFAULT_DATE_TIME_FORMAT.toString()))
            .andExpect(jsonPath("$.operatingSystem").value(DEFAULT_OPERATING_SYSTEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSiemLogType() throws Exception {
        // Get the siemLogType
        restSiemLogTypeMockMvc.perform(get("/api/siem-log-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiemLogType() throws Exception {
        // Initialize the database
        siemLogTypeRepository.saveAndFlush(siemLogType);

        int databaseSizeBeforeUpdate = siemLogTypeRepository.findAll().size();

        // Update the siemLogType
        SiemLogType updatedSiemLogType = siemLogTypeRepository.findById(siemLogType.getId()).get();
        // Disconnect from session so that the updates on updatedSiemLogType are not directly saved in db
        em.detach(updatedSiemLogType);
        updatedSiemLogType
            .timestampRegex(UPDATED_TIMESTAMP_REGEX)
            .severityRegex(UPDATED_SEVERITY_REGEX)
            .contextRegex(UPDATED_CONTEXT_REGEX)
            .messageRegex(UPDATED_MESSAGE_REGEX)
            .name(UPDATED_NAME)
            .dateTimeFormat(UPDATED_DATE_TIME_FORMAT)
            .operatingSystem(UPDATED_OPERATING_SYSTEM);
        SiemLogTypeDTO siemLogTypeDTO = siemLogTypeMapper.toDto(updatedSiemLogType);

        restSiemLogTypeMockMvc.perform(put("/api/siem-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemLogTypeDTO)))
            .andExpect(status().isOk());

        // Validate the SiemLogType in the database
        List<SiemLogType> siemLogTypeList = siemLogTypeRepository.findAll();
        assertThat(siemLogTypeList).hasSize(databaseSizeBeforeUpdate);
        SiemLogType testSiemLogType = siemLogTypeList.get(siemLogTypeList.size() - 1);
        assertThat(testSiemLogType.getTimestampRegex()).isEqualTo(UPDATED_TIMESTAMP_REGEX);
        assertThat(testSiemLogType.getSeverityRegex()).isEqualTo(UPDATED_SEVERITY_REGEX);
        assertThat(testSiemLogType.getContextRegex()).isEqualTo(UPDATED_CONTEXT_REGEX);
        assertThat(testSiemLogType.getMessageRegex()).isEqualTo(UPDATED_MESSAGE_REGEX);
        assertThat(testSiemLogType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSiemLogType.getDateTimeFormat()).isEqualTo(UPDATED_DATE_TIME_FORMAT);
        assertThat(testSiemLogType.getOperatingSystem()).isEqualTo(UPDATED_OPERATING_SYSTEM);

        // Validate the SiemLogType in Elasticsearch
        verify(mockSiemLogTypeSearchRepository, times(1)).save(testSiemLogType);
    }

    @Test
    @Transactional
    public void updateNonExistingSiemLogType() throws Exception {
        int databaseSizeBeforeUpdate = siemLogTypeRepository.findAll().size();

        // Create the SiemLogType
        SiemLogTypeDTO siemLogTypeDTO = siemLogTypeMapper.toDto(siemLogType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiemLogTypeMockMvc.perform(put("/api/siem-log-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemLogTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiemLogType in the database
        List<SiemLogType> siemLogTypeList = siemLogTypeRepository.findAll();
        assertThat(siemLogTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SiemLogType in Elasticsearch
        verify(mockSiemLogTypeSearchRepository, times(0)).save(siemLogType);
    }

    @Test
    @Transactional
    public void deleteSiemLogType() throws Exception {
        // Initialize the database
        siemLogTypeRepository.saveAndFlush(siemLogType);

        int databaseSizeBeforeDelete = siemLogTypeRepository.findAll().size();

        // Delete the siemLogType
        restSiemLogTypeMockMvc.perform(delete("/api/siem-log-types/{id}", siemLogType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SiemLogType> siemLogTypeList = siemLogTypeRepository.findAll();
        assertThat(siemLogTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SiemLogType in Elasticsearch
        verify(mockSiemLogTypeSearchRepository, times(1)).deleteById(siemLogType.getId());
    }

    @Test
    @Transactional
    public void searchSiemLogType() throws Exception {
        // Initialize the database
        siemLogTypeRepository.saveAndFlush(siemLogType);
        when(mockSiemLogTypeSearchRepository.search(queryStringQuery("id:" + siemLogType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(siemLogType), PageRequest.of(0, 1), 1));
        // Search the siemLogType
        restSiemLogTypeMockMvc.perform(get("/api/_search/siem-log-types?query=id:" + siemLogType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siemLogType.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestampRegex").value(hasItem(DEFAULT_TIMESTAMP_REGEX)))
            .andExpect(jsonPath("$.[*].severityRegex").value(hasItem(DEFAULT_SEVERITY_REGEX)))
            .andExpect(jsonPath("$.[*].contextRegex").value(hasItem(DEFAULT_CONTEXT_REGEX)))
            .andExpect(jsonPath("$.[*].messageRegex").value(hasItem(DEFAULT_MESSAGE_REGEX)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dateTimeFormat").value(hasItem(DEFAULT_DATE_TIME_FORMAT)))
            .andExpect(jsonPath("$.[*].operatingSystem").value(hasItem(DEFAULT_OPERATING_SYSTEM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiemLogType.class);
        SiemLogType siemLogType1 = new SiemLogType();
        siemLogType1.setId(1L);
        SiemLogType siemLogType2 = new SiemLogType();
        siemLogType2.setId(siemLogType1.getId());
        assertThat(siemLogType1).isEqualTo(siemLogType2);
        siemLogType2.setId(2L);
        assertThat(siemLogType1).isNotEqualTo(siemLogType2);
        siemLogType1.setId(null);
        assertThat(siemLogType1).isNotEqualTo(siemLogType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiemLogTypeDTO.class);
        SiemLogTypeDTO siemLogTypeDTO1 = new SiemLogTypeDTO();
        siemLogTypeDTO1.setId(1L);
        SiemLogTypeDTO siemLogTypeDTO2 = new SiemLogTypeDTO();
        assertThat(siemLogTypeDTO1).isNotEqualTo(siemLogTypeDTO2);
        siemLogTypeDTO2.setId(siemLogTypeDTO1.getId());
        assertThat(siemLogTypeDTO1).isEqualTo(siemLogTypeDTO2);
        siemLogTypeDTO2.setId(2L);
        assertThat(siemLogTypeDTO1).isNotEqualTo(siemLogTypeDTO2);
        siemLogTypeDTO1.setId(null);
        assertThat(siemLogTypeDTO1).isNotEqualTo(siemLogTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(siemLogTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(siemLogTypeMapper.fromId(null)).isNull();
    }
}
