package com.team20.siemcenter.web.rest;

import com.team20.siemcenter.SiemcenterApp;

import com.team20.siemcenter.domain.AlarmDefinition;
import com.team20.siemcenter.repository.AlarmDefinitionRepository;
import com.team20.siemcenter.repository.search.AlarmDefinitionSearchRepository;
import com.team20.siemcenter.service.AlarmDefinitionService;
import com.team20.siemcenter.service.dto.AlarmDefinitionDTO;
import com.team20.siemcenter.service.mapper.AlarmDefinitionMapper;
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

import com.team20.siemcenter.domain.enumeration.AlarmRadius;
import com.team20.siemcenter.domain.enumeration.SiemLogSeverityEnum;
import com.team20.siemcenter.domain.enumeration.SiemLogSourceEnum;
import com.team20.siemcenter.domain.enumeration.OperatingSystem;
/**
 * Test class for the AlarmDefinitionResource REST controller.
 *
 * @see AlarmDefinitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiemcenterApp.class)
public class AlarmDefinitionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_REPEATS = 1;
    private static final Integer UPDATED_NUM_REPEATS = 2;

    private static final Long DEFAULT_TIME_SPAN = 1L;
    private static final Long UPDATED_TIME_SPAN = 2L;

    private static final String DEFAULT_KEYWORD = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELDNAME = "BBBBBBBBBB";

    private static final AlarmRadius DEFAULT_ALARM_RADIUS = AlarmRadius.SYSTEM;
    private static final AlarmRadius UPDATED_ALARM_RADIUS = AlarmRadius.AGENT;

    private static final SiemLogSeverityEnum DEFAULT_SEVERITY = SiemLogSeverityEnum.TRACE;
    private static final SiemLogSeverityEnum UPDATED_SEVERITY = SiemLogSeverityEnum.DEBUG;

    private static final SiemLogSourceEnum DEFAULT_LOG_SOURCE = SiemLogSourceEnum.WEBSERVER;
    private static final SiemLogSourceEnum UPDATED_LOG_SOURCE = SiemLogSourceEnum.APPLICATION;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final OperatingSystem DEFAULT_OPERATING_SYSTEM = OperatingSystem.WINDOWS;
    private static final OperatingSystem UPDATED_OPERATING_SYSTEM = OperatingSystem.LINUX;

    @Autowired
    private AlarmDefinitionRepository alarmDefinitionRepository;

    @Autowired
    private AlarmDefinitionMapper alarmDefinitionMapper;

    @Autowired
    private AlarmDefinitionService alarmDefinitionService;

    /**
     * This repository is mocked in the com.team20.siemcenter.repository.search test package.
     *
     * @see com.team20.siemcenter.repository.search.AlarmDefinitionSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlarmDefinitionSearchRepository mockAlarmDefinitionSearchRepository;

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

    private MockMvc restAlarmDefinitionMockMvc;

    private AlarmDefinition alarmDefinition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlarmDefinitionResource alarmDefinitionResource = new AlarmDefinitionResource(alarmDefinitionService);
        this.restAlarmDefinitionMockMvc = MockMvcBuilders.standaloneSetup(alarmDefinitionResource)
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
    public static AlarmDefinition createEntity(EntityManager em) {
        AlarmDefinition alarmDefinition = new AlarmDefinition()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .numRepeats(DEFAULT_NUM_REPEATS)
            .timeSpan(DEFAULT_TIME_SPAN)
            .keyword(DEFAULT_KEYWORD)
            .fieldname(DEFAULT_FIELDNAME)
            .alarmRadius(DEFAULT_ALARM_RADIUS)
            .severity(DEFAULT_SEVERITY)
            .logSource(DEFAULT_LOG_SOURCE)
            .active(DEFAULT_ACTIVE)
            .operatingSystem(DEFAULT_OPERATING_SYSTEM);
        return alarmDefinition;
    }

    @Before
    public void initTest() {
        alarmDefinition = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarmDefinition() throws Exception {
        int databaseSizeBeforeCreate = alarmDefinitionRepository.findAll().size();

        // Create the AlarmDefinition
        AlarmDefinitionDTO alarmDefinitionDTO = alarmDefinitionMapper.toDto(alarmDefinition);
        restAlarmDefinitionMockMvc.perform(post("/api/alarm-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmDefinitionDTO)))
            .andExpect(status().isCreated());

        // Validate the AlarmDefinition in the database
        List<AlarmDefinition> alarmDefinitionList = alarmDefinitionRepository.findAll();
        assertThat(alarmDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        AlarmDefinition testAlarmDefinition = alarmDefinitionList.get(alarmDefinitionList.size() - 1);
        assertThat(testAlarmDefinition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlarmDefinition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlarmDefinition.getNumRepeats()).isEqualTo(DEFAULT_NUM_REPEATS);
        assertThat(testAlarmDefinition.getTimeSpan()).isEqualTo(DEFAULT_TIME_SPAN);
        assertThat(testAlarmDefinition.getKeyword()).isEqualTo(DEFAULT_KEYWORD);
        assertThat(testAlarmDefinition.getFieldname()).isEqualTo(DEFAULT_FIELDNAME);
        assertThat(testAlarmDefinition.getAlarmRadius()).isEqualTo(DEFAULT_ALARM_RADIUS);
        assertThat(testAlarmDefinition.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testAlarmDefinition.getLogSource()).isEqualTo(DEFAULT_LOG_SOURCE);
        assertThat(testAlarmDefinition.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAlarmDefinition.getOperatingSystem()).isEqualTo(DEFAULT_OPERATING_SYSTEM);

        // Validate the AlarmDefinition in Elasticsearch
        verify(mockAlarmDefinitionSearchRepository, times(1)).save(testAlarmDefinition);
    }

    @Test
    @Transactional
    public void createAlarmDefinitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmDefinitionRepository.findAll().size();

        // Create the AlarmDefinition with an existing ID
        alarmDefinition.setId(1L);
        AlarmDefinitionDTO alarmDefinitionDTO = alarmDefinitionMapper.toDto(alarmDefinition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmDefinitionMockMvc.perform(post("/api/alarm-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmDefinitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmDefinition in the database
        List<AlarmDefinition> alarmDefinitionList = alarmDefinitionRepository.findAll();
        assertThat(alarmDefinitionList).hasSize(databaseSizeBeforeCreate);

        // Validate the AlarmDefinition in Elasticsearch
        verify(mockAlarmDefinitionSearchRepository, times(0)).save(alarmDefinition);
    }

    @Test
    @Transactional
    public void getAllAlarmDefinitions() throws Exception {
        // Initialize the database
        alarmDefinitionRepository.saveAndFlush(alarmDefinition);

        // Get all the alarmDefinitionList
        restAlarmDefinitionMockMvc.perform(get("/api/alarm-definitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].numRepeats").value(hasItem(DEFAULT_NUM_REPEATS)))
            .andExpect(jsonPath("$.[*].timeSpan").value(hasItem(DEFAULT_TIME_SPAN.intValue())))
            .andExpect(jsonPath("$.[*].keyword").value(hasItem(DEFAULT_KEYWORD.toString())))
            .andExpect(jsonPath("$.[*].fieldname").value(hasItem(DEFAULT_FIELDNAME.toString())))
            .andExpect(jsonPath("$.[*].alarmRadius").value(hasItem(DEFAULT_ALARM_RADIUS.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].logSource").value(hasItem(DEFAULT_LOG_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].operatingSystem").value(hasItem(DEFAULT_OPERATING_SYSTEM.toString())));
    }
    
    @Test
    @Transactional
    public void getAlarmDefinition() throws Exception {
        // Initialize the database
        alarmDefinitionRepository.saveAndFlush(alarmDefinition);

        // Get the alarmDefinition
        restAlarmDefinitionMockMvc.perform(get("/api/alarm-definitions/{id}", alarmDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alarmDefinition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.numRepeats").value(DEFAULT_NUM_REPEATS))
            .andExpect(jsonPath("$.timeSpan").value(DEFAULT_TIME_SPAN.intValue()))
            .andExpect(jsonPath("$.keyword").value(DEFAULT_KEYWORD.toString()))
            .andExpect(jsonPath("$.fieldname").value(DEFAULT_FIELDNAME.toString()))
            .andExpect(jsonPath("$.alarmRadius").value(DEFAULT_ALARM_RADIUS.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.logSource").value(DEFAULT_LOG_SOURCE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.operatingSystem").value(DEFAULT_OPERATING_SYSTEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlarmDefinition() throws Exception {
        // Get the alarmDefinition
        restAlarmDefinitionMockMvc.perform(get("/api/alarm-definitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarmDefinition() throws Exception {
        // Initialize the database
        alarmDefinitionRepository.saveAndFlush(alarmDefinition);

        int databaseSizeBeforeUpdate = alarmDefinitionRepository.findAll().size();

        // Update the alarmDefinition
        AlarmDefinition updatedAlarmDefinition = alarmDefinitionRepository.findById(alarmDefinition.getId()).get();
        // Disconnect from session so that the updates on updatedAlarmDefinition are not directly saved in db
        em.detach(updatedAlarmDefinition);
        updatedAlarmDefinition
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .numRepeats(UPDATED_NUM_REPEATS)
            .timeSpan(UPDATED_TIME_SPAN)
            .keyword(UPDATED_KEYWORD)
            .fieldname(UPDATED_FIELDNAME)
            .alarmRadius(UPDATED_ALARM_RADIUS)
            .severity(UPDATED_SEVERITY)
            .logSource(UPDATED_LOG_SOURCE)
            .active(UPDATED_ACTIVE)
            .operatingSystem(UPDATED_OPERATING_SYSTEM);
        AlarmDefinitionDTO alarmDefinitionDTO = alarmDefinitionMapper.toDto(updatedAlarmDefinition);

        restAlarmDefinitionMockMvc.perform(put("/api/alarm-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmDefinitionDTO)))
            .andExpect(status().isOk());

        // Validate the AlarmDefinition in the database
        List<AlarmDefinition> alarmDefinitionList = alarmDefinitionRepository.findAll();
        assertThat(alarmDefinitionList).hasSize(databaseSizeBeforeUpdate);
        AlarmDefinition testAlarmDefinition = alarmDefinitionList.get(alarmDefinitionList.size() - 1);
        assertThat(testAlarmDefinition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlarmDefinition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlarmDefinition.getNumRepeats()).isEqualTo(UPDATED_NUM_REPEATS);
        assertThat(testAlarmDefinition.getTimeSpan()).isEqualTo(UPDATED_TIME_SPAN);
        assertThat(testAlarmDefinition.getKeyword()).isEqualTo(UPDATED_KEYWORD);
        assertThat(testAlarmDefinition.getFieldname()).isEqualTo(UPDATED_FIELDNAME);
        assertThat(testAlarmDefinition.getAlarmRadius()).isEqualTo(UPDATED_ALARM_RADIUS);
        assertThat(testAlarmDefinition.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testAlarmDefinition.getLogSource()).isEqualTo(UPDATED_LOG_SOURCE);
        assertThat(testAlarmDefinition.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAlarmDefinition.getOperatingSystem()).isEqualTo(UPDATED_OPERATING_SYSTEM);

        // Validate the AlarmDefinition in Elasticsearch
        verify(mockAlarmDefinitionSearchRepository, times(1)).save(testAlarmDefinition);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarmDefinition() throws Exception {
        int databaseSizeBeforeUpdate = alarmDefinitionRepository.findAll().size();

        // Create the AlarmDefinition
        AlarmDefinitionDTO alarmDefinitionDTO = alarmDefinitionMapper.toDto(alarmDefinition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmDefinitionMockMvc.perform(put("/api/alarm-definitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmDefinitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmDefinition in the database
        List<AlarmDefinition> alarmDefinitionList = alarmDefinitionRepository.findAll();
        assertThat(alarmDefinitionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AlarmDefinition in Elasticsearch
        verify(mockAlarmDefinitionSearchRepository, times(0)).save(alarmDefinition);
    }

    @Test
    @Transactional
    public void deleteAlarmDefinition() throws Exception {
        // Initialize the database
        alarmDefinitionRepository.saveAndFlush(alarmDefinition);

        int databaseSizeBeforeDelete = alarmDefinitionRepository.findAll().size();

        // Delete the alarmDefinition
        restAlarmDefinitionMockMvc.perform(delete("/api/alarm-definitions/{id}", alarmDefinition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AlarmDefinition> alarmDefinitionList = alarmDefinitionRepository.findAll();
        assertThat(alarmDefinitionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AlarmDefinition in Elasticsearch
        verify(mockAlarmDefinitionSearchRepository, times(1)).deleteById(alarmDefinition.getId());
    }

    @Test
    @Transactional
    public void searchAlarmDefinition() throws Exception {
        // Initialize the database
        alarmDefinitionRepository.saveAndFlush(alarmDefinition);
        when(mockAlarmDefinitionSearchRepository.search(queryStringQuery("id:" + alarmDefinition.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(alarmDefinition), PageRequest.of(0, 1), 1));
        // Search the alarmDefinition
        restAlarmDefinitionMockMvc.perform(get("/api/_search/alarm-definitions?query=id:" + alarmDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].numRepeats").value(hasItem(DEFAULT_NUM_REPEATS)))
            .andExpect(jsonPath("$.[*].timeSpan").value(hasItem(DEFAULT_TIME_SPAN.intValue())))
            .andExpect(jsonPath("$.[*].keyword").value(hasItem(DEFAULT_KEYWORD)))
            .andExpect(jsonPath("$.[*].fieldname").value(hasItem(DEFAULT_FIELDNAME)))
            .andExpect(jsonPath("$.[*].alarmRadius").value(hasItem(DEFAULT_ALARM_RADIUS.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].logSource").value(hasItem(DEFAULT_LOG_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].operatingSystem").value(hasItem(DEFAULT_OPERATING_SYSTEM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmDefinition.class);
        AlarmDefinition alarmDefinition1 = new AlarmDefinition();
        alarmDefinition1.setId(1L);
        AlarmDefinition alarmDefinition2 = new AlarmDefinition();
        alarmDefinition2.setId(alarmDefinition1.getId());
        assertThat(alarmDefinition1).isEqualTo(alarmDefinition2);
        alarmDefinition2.setId(2L);
        assertThat(alarmDefinition1).isNotEqualTo(alarmDefinition2);
        alarmDefinition1.setId(null);
        assertThat(alarmDefinition1).isNotEqualTo(alarmDefinition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmDefinitionDTO.class);
        AlarmDefinitionDTO alarmDefinitionDTO1 = new AlarmDefinitionDTO();
        alarmDefinitionDTO1.setId(1L);
        AlarmDefinitionDTO alarmDefinitionDTO2 = new AlarmDefinitionDTO();
        assertThat(alarmDefinitionDTO1).isNotEqualTo(alarmDefinitionDTO2);
        alarmDefinitionDTO2.setId(alarmDefinitionDTO1.getId());
        assertThat(alarmDefinitionDTO1).isEqualTo(alarmDefinitionDTO2);
        alarmDefinitionDTO2.setId(2L);
        assertThat(alarmDefinitionDTO1).isNotEqualTo(alarmDefinitionDTO2);
        alarmDefinitionDTO1.setId(null);
        assertThat(alarmDefinitionDTO1).isNotEqualTo(alarmDefinitionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(alarmDefinitionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(alarmDefinitionMapper.fromId(null)).isNull();
    }
}
