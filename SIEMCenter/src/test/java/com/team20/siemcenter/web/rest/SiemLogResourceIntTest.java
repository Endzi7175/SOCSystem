package com.team20.siemcenter.web.rest;

import com.team20.siemcenter.SiemcenterApp;

import com.team20.siemcenter.domain.SiemLog;
import com.team20.siemcenter.repository.SiemLogRepository;
import com.team20.siemcenter.repository.search.SiemLogSearchRepository;
import com.team20.siemcenter.service.SiemLogService;
import com.team20.siemcenter.service.dto.SiemLogDTO;
import com.team20.siemcenter.service.mapper.SiemLogMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import com.team20.siemcenter.domain.enumeration.SiemLogSeverityEnum;
import com.team20.siemcenter.domain.enumeration.SiemLogSourceEnum;
/**
 * Test class for the SiemLogResource REST controller.
 *
 * @see SiemLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiemcenterApp.class)
public class SiemLogResourceIntTest {

    private static final OperatingSystem DEFAULT_OPERATING_SYSTEM = OperatingSystem.WINDOWS;
    private static final OperatingSystem UPDATED_OPERATING_SYSTEM = OperatingSystem.LINUX;

    private static final String DEFAULT_OPERATING_SYSTEM_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_OPERATING_SYSTEM_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_IP = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_IP = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_IP = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_IP = "BBBBBBBBBB";

    private static final String DEFAULT_HOST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DIRECTORY = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTORY = "BBBBBBBBBB";

    private static final SiemLogSeverityEnum DEFAULT_SEVERITY = SiemLogSeverityEnum.TRACE;
    private static final SiemLogSeverityEnum UPDATED_SEVERITY = SiemLogSeverityEnum.DEBUG;

    private static final SiemLogSourceEnum DEFAULT_SOURCE = SiemLogSourceEnum.WEBSERVER;
    private static final SiemLogSourceEnum UPDATED_SOURCE = SiemLogSourceEnum.APPLICATION;

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_RAW_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_RAW_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private SiemLogRepository siemLogRepository;

    @Autowired
    private SiemLogMapper siemLogMapper;

    @Autowired
    private SiemLogService siemLogService;

    /**
     * This repository is mocked in the com.team20.siemcenter.repository.search test package.
     *
     * @see com.team20.siemcenter.repository.search.SiemLogSearchRepositoryMockConfiguration
     */
    @Autowired
    private SiemLogSearchRepository mockSiemLogSearchRepository;

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

    private MockMvc restSiemLogMockMvc;

    private SiemLog siemLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SiemLogResource siemLogResource = new SiemLogResource(siemLogService);
        this.restSiemLogMockMvc = MockMvcBuilders.standaloneSetup(siemLogResource)
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
    public static SiemLog createEntity(EntityManager em) {
        SiemLog siemLog = new SiemLog()
            .operatingSystem(DEFAULT_OPERATING_SYSTEM)
            .operatingSystemVersion(DEFAULT_OPERATING_SYSTEM_VERSION)
            .internalIp(DEFAULT_INTERNAL_IP)
            .externalIp(DEFAULT_EXTERNAL_IP)
            .hostName(DEFAULT_HOST_NAME)
            .context(DEFAULT_CONTEXT)
            .message(DEFAULT_MESSAGE)
            .timestamp(DEFAULT_TIMESTAMP)
            .directory(DEFAULT_DIRECTORY)
            .severity(DEFAULT_SEVERITY)
            .source(DEFAULT_SOURCE)
            .filePath(DEFAULT_FILE_PATH)
            .rawMessage(DEFAULT_RAW_MESSAGE);
        return siemLog;
    }

    @Before
    public void initTest() {
        siemLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiemLog() throws Exception {
        int databaseSizeBeforeCreate = siemLogRepository.findAll().size();

        // Create the SiemLog
        SiemLogDTO siemLogDTO = siemLogMapper.toDto(siemLog);
        restSiemLogMockMvc.perform(post("/api/siem-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemLogDTO)))
            .andExpect(status().isCreated());

        // Validate the SiemLog in the database
        List<SiemLog> siemLogList = siemLogRepository.findAll();
        assertThat(siemLogList).hasSize(databaseSizeBeforeCreate + 1);
        SiemLog testSiemLog = siemLogList.get(siemLogList.size() - 1);
        assertThat(testSiemLog.getOperatingSystem()).isEqualTo(DEFAULT_OPERATING_SYSTEM);
        assertThat(testSiemLog.getOperatingSystemVersion()).isEqualTo(DEFAULT_OPERATING_SYSTEM_VERSION);
        assertThat(testSiemLog.getInternalIp()).isEqualTo(DEFAULT_INTERNAL_IP);
        assertThat(testSiemLog.getExternalIp()).isEqualTo(DEFAULT_EXTERNAL_IP);
        assertThat(testSiemLog.getHostName()).isEqualTo(DEFAULT_HOST_NAME);
        assertThat(testSiemLog.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testSiemLog.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testSiemLog.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testSiemLog.getDirectory()).isEqualTo(DEFAULT_DIRECTORY);
        assertThat(testSiemLog.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testSiemLog.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testSiemLog.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testSiemLog.getRawMessage()).isEqualTo(DEFAULT_RAW_MESSAGE);

        // Validate the SiemLog in Elasticsearch
        verify(mockSiemLogSearchRepository, times(1)).save(testSiemLog);
    }

    @Test
    @Transactional
    public void createSiemLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siemLogRepository.findAll().size();

        // Create the SiemLog with an existing ID
        siemLog.setId(1L);
        SiemLogDTO siemLogDTO = siemLogMapper.toDto(siemLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiemLogMockMvc.perform(post("/api/siem-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiemLog in the database
        List<SiemLog> siemLogList = siemLogRepository.findAll();
        assertThat(siemLogList).hasSize(databaseSizeBeforeCreate);

        // Validate the SiemLog in Elasticsearch
        verify(mockSiemLogSearchRepository, times(0)).save(siemLog);
    }

    @Test
    @Transactional
    public void getAllSiemLogs() throws Exception {
        // Initialize the database
        siemLogRepository.saveAndFlush(siemLog);

        // Get all the siemLogList
        restSiemLogMockMvc.perform(get("/api/siem-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siemLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].operatingSystem").value(hasItem(DEFAULT_OPERATING_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].operatingSystemVersion").value(hasItem(DEFAULT_OPERATING_SYSTEM_VERSION.toString())))
            .andExpect(jsonPath("$.[*].internalIp").value(hasItem(DEFAULT_INTERNAL_IP.toString())))
            .andExpect(jsonPath("$.[*].externalIp").value(hasItem(DEFAULT_EXTERNAL_IP.toString())))
            .andExpect(jsonPath("$.[*].hostName").value(hasItem(DEFAULT_HOST_NAME.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].directory").value(hasItem(DEFAULT_DIRECTORY.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH.toString())))
            .andExpect(jsonPath("$.[*].rawMessage").value(hasItem(DEFAULT_RAW_MESSAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getSiemLog() throws Exception {
        // Initialize the database
        siemLogRepository.saveAndFlush(siemLog);

        // Get the siemLog
        restSiemLogMockMvc.perform(get("/api/siem-logs/{id}", siemLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(siemLog.getId().intValue()))
            .andExpect(jsonPath("$.operatingSystem").value(DEFAULT_OPERATING_SYSTEM.toString()))
            .andExpect(jsonPath("$.operatingSystemVersion").value(DEFAULT_OPERATING_SYSTEM_VERSION.toString()))
            .andExpect(jsonPath("$.internalIp").value(DEFAULT_INTERNAL_IP.toString()))
            .andExpect(jsonPath("$.externalIp").value(DEFAULT_EXTERNAL_IP.toString()))
            .andExpect(jsonPath("$.hostName").value(DEFAULT_HOST_NAME.toString()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.directory").value(DEFAULT_DIRECTORY.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH.toString()))
            .andExpect(jsonPath("$.rawMessage").value(DEFAULT_RAW_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSiemLog() throws Exception {
        // Get the siemLog
        restSiemLogMockMvc.perform(get("/api/siem-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiemLog() throws Exception {
        // Initialize the database
        siemLogRepository.saveAndFlush(siemLog);

        int databaseSizeBeforeUpdate = siemLogRepository.findAll().size();

        // Update the siemLog
        SiemLog updatedSiemLog = siemLogRepository.findById(siemLog.getId()).get();
        // Disconnect from session so that the updates on updatedSiemLog are not directly saved in db
        em.detach(updatedSiemLog);
        updatedSiemLog
            .operatingSystem(UPDATED_OPERATING_SYSTEM)
            .operatingSystemVersion(UPDATED_OPERATING_SYSTEM_VERSION)
            .internalIp(UPDATED_INTERNAL_IP)
            .externalIp(UPDATED_EXTERNAL_IP)
            .hostName(UPDATED_HOST_NAME)
            .context(UPDATED_CONTEXT)
            .message(UPDATED_MESSAGE)
            .timestamp(UPDATED_TIMESTAMP)
            .directory(UPDATED_DIRECTORY)
            .severity(UPDATED_SEVERITY)
            .source(UPDATED_SOURCE)
            .filePath(UPDATED_FILE_PATH)
            .rawMessage(UPDATED_RAW_MESSAGE);
        SiemLogDTO siemLogDTO = siemLogMapper.toDto(updatedSiemLog);

        restSiemLogMockMvc.perform(put("/api/siem-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemLogDTO)))
            .andExpect(status().isOk());

        // Validate the SiemLog in the database
        List<SiemLog> siemLogList = siemLogRepository.findAll();
        assertThat(siemLogList).hasSize(databaseSizeBeforeUpdate);
        SiemLog testSiemLog = siemLogList.get(siemLogList.size() - 1);
        assertThat(testSiemLog.getOperatingSystem()).isEqualTo(UPDATED_OPERATING_SYSTEM);
        assertThat(testSiemLog.getOperatingSystemVersion()).isEqualTo(UPDATED_OPERATING_SYSTEM_VERSION);
        assertThat(testSiemLog.getInternalIp()).isEqualTo(UPDATED_INTERNAL_IP);
        assertThat(testSiemLog.getExternalIp()).isEqualTo(UPDATED_EXTERNAL_IP);
        assertThat(testSiemLog.getHostName()).isEqualTo(UPDATED_HOST_NAME);
        assertThat(testSiemLog.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testSiemLog.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testSiemLog.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testSiemLog.getDirectory()).isEqualTo(UPDATED_DIRECTORY);
        assertThat(testSiemLog.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testSiemLog.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testSiemLog.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testSiemLog.getRawMessage()).isEqualTo(UPDATED_RAW_MESSAGE);

        // Validate the SiemLog in Elasticsearch
        verify(mockSiemLogSearchRepository, times(1)).save(testSiemLog);
    }

    @Test
    @Transactional
    public void updateNonExistingSiemLog() throws Exception {
        int databaseSizeBeforeUpdate = siemLogRepository.findAll().size();

        // Create the SiemLog
        SiemLogDTO siemLogDTO = siemLogMapper.toDto(siemLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiemLogMockMvc.perform(put("/api/siem-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siemLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiemLog in the database
        List<SiemLog> siemLogList = siemLogRepository.findAll();
        assertThat(siemLogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SiemLog in Elasticsearch
        verify(mockSiemLogSearchRepository, times(0)).save(siemLog);
    }

    @Test
    @Transactional
    public void deleteSiemLog() throws Exception {
        // Initialize the database
        siemLogRepository.saveAndFlush(siemLog);

        int databaseSizeBeforeDelete = siemLogRepository.findAll().size();

        // Delete the siemLog
        restSiemLogMockMvc.perform(delete("/api/siem-logs/{id}", siemLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SiemLog> siemLogList = siemLogRepository.findAll();
        assertThat(siemLogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SiemLog in Elasticsearch
        verify(mockSiemLogSearchRepository, times(1)).deleteById(siemLog.getId());
    }

    @Test
    @Transactional
    public void searchSiemLog() throws Exception {
        // Initialize the database
        siemLogRepository.saveAndFlush(siemLog);
        when(mockSiemLogSearchRepository.search(queryStringQuery("id:" + siemLog.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(siemLog), PageRequest.of(0, 1), 1));
        // Search the siemLog
        restSiemLogMockMvc.perform(get("/api/_search/siem-logs?query=id:" + siemLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siemLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].operatingSystem").value(hasItem(DEFAULT_OPERATING_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].operatingSystemVersion").value(hasItem(DEFAULT_OPERATING_SYSTEM_VERSION)))
            .andExpect(jsonPath("$.[*].internalIp").value(hasItem(DEFAULT_INTERNAL_IP)))
            .andExpect(jsonPath("$.[*].externalIp").value(hasItem(DEFAULT_EXTERNAL_IP)))
            .andExpect(jsonPath("$.[*].hostName").value(hasItem(DEFAULT_HOST_NAME)))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].directory").value(hasItem(DEFAULT_DIRECTORY)))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].rawMessage").value(hasItem(DEFAULT_RAW_MESSAGE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiemLog.class);
        SiemLog siemLog1 = new SiemLog();
        siemLog1.setId(1L);
        SiemLog siemLog2 = new SiemLog();
        siemLog2.setId(siemLog1.getId());
        assertThat(siemLog1).isEqualTo(siemLog2);
        siemLog2.setId(2L);
        assertThat(siemLog1).isNotEqualTo(siemLog2);
        siemLog1.setId(null);
        assertThat(siemLog1).isNotEqualTo(siemLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiemLogDTO.class);
        SiemLogDTO siemLogDTO1 = new SiemLogDTO();
        siemLogDTO1.setId(1L);
        SiemLogDTO siemLogDTO2 = new SiemLogDTO();
        assertThat(siemLogDTO1).isNotEqualTo(siemLogDTO2);
        siemLogDTO2.setId(siemLogDTO1.getId());
        assertThat(siemLogDTO1).isEqualTo(siemLogDTO2);
        siemLogDTO2.setId(2L);
        assertThat(siemLogDTO1).isNotEqualTo(siemLogDTO2);
        siemLogDTO1.setId(null);
        assertThat(siemLogDTO1).isNotEqualTo(siemLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(siemLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(siemLogMapper.fromId(null)).isNull();
    }
}
