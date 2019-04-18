package com.team20.siemcenter.web.rest;

import com.team20.siemcenter.SiemcenterApp;

import com.team20.siemcenter.domain.Alarm;
import com.team20.siemcenter.repository.AlarmRepository;
import com.team20.siemcenter.repository.search.AlarmSearchRepository;
import com.team20.siemcenter.service.AlarmService;
import com.team20.siemcenter.service.dto.AlarmDTO;
import com.team20.siemcenter.service.mapper.AlarmMapper;
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

/**
 * Test class for the AlarmResource REST controller.
 *
 * @see AlarmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiemcenterApp.class)
public class AlarmResourceIntTest {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final Integer DEFAULT_REPEAT_COUNT = 1;
    private static final Integer UPDATED_REPEAT_COUNT = 2;

    private static final Instant DEFAULT_FIRST_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIRST_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DISMISSED = false;
    private static final Boolean UPDATED_DISMISSED = true;

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired
    private AlarmService alarmService;

    /**
     * This repository is mocked in the com.team20.siemcenter.repository.search test package.
     *
     * @see com.team20.siemcenter.repository.search.AlarmSearchRepositoryMockConfiguration
     */
    @Autowired
    private AlarmSearchRepository mockAlarmSearchRepository;

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

    private MockMvc restAlarmMockMvc;

    private Alarm alarm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlarmResource alarmResource = new AlarmResource(alarmService);
        this.restAlarmMockMvc = MockMvcBuilders.standaloneSetup(alarmResource)
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
    public static Alarm createEntity(EntityManager em) {
        Alarm alarm = new Alarm()
            .message(DEFAULT_MESSAGE)
            .timestamp(DEFAULT_TIMESTAMP)
            .activated(DEFAULT_ACTIVATED)
            .repeatCount(DEFAULT_REPEAT_COUNT)
            .firstTimestamp(DEFAULT_FIRST_TIMESTAMP)
            .dismissed(DEFAULT_DISMISSED)
            .context(DEFAULT_CONTEXT);
        return alarm;
    }

    @Before
    public void initTest() {
        alarm = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarm() throws Exception {
        int databaseSizeBeforeCreate = alarmRepository.findAll().size();

        // Create the Alarm
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);
        restAlarmMockMvc.perform(post("/api/alarms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isCreated());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeCreate + 1);
        Alarm testAlarm = alarmList.get(alarmList.size() - 1);
        assertThat(testAlarm.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testAlarm.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testAlarm.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testAlarm.getRepeatCount()).isEqualTo(DEFAULT_REPEAT_COUNT);
        assertThat(testAlarm.getFirstTimestamp()).isEqualTo(DEFAULT_FIRST_TIMESTAMP);
        assertThat(testAlarm.isDismissed()).isEqualTo(DEFAULT_DISMISSED);
        assertThat(testAlarm.getContext()).isEqualTo(DEFAULT_CONTEXT);

        // Validate the Alarm in Elasticsearch
        verify(mockAlarmSearchRepository, times(1)).save(testAlarm);
    }

    @Test
    @Transactional
    public void createAlarmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmRepository.findAll().size();

        // Create the Alarm with an existing ID
        alarm.setId(1L);
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmMockMvc.perform(post("/api/alarms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeCreate);

        // Validate the Alarm in Elasticsearch
        verify(mockAlarmSearchRepository, times(0)).save(alarm);
    }

    @Test
    @Transactional
    public void getAllAlarms() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        // Get all the alarmList
        restAlarmMockMvc.perform(get("/api/alarms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarm.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].repeatCount").value(hasItem(DEFAULT_REPEAT_COUNT)))
            .andExpect(jsonPath("$.[*].firstTimestamp").value(hasItem(DEFAULT_FIRST_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].dismissed").value(hasItem(DEFAULT_DISMISSED.booleanValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())));
    }
    
    @Test
    @Transactional
    public void getAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        // Get the alarm
        restAlarmMockMvc.perform(get("/api/alarms/{id}", alarm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alarm.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.repeatCount").value(DEFAULT_REPEAT_COUNT))
            .andExpect(jsonPath("$.firstTimestamp").value(DEFAULT_FIRST_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.dismissed").value(DEFAULT_DISMISSED.booleanValue()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlarm() throws Exception {
        // Get the alarm
        restAlarmMockMvc.perform(get("/api/alarms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        int databaseSizeBeforeUpdate = alarmRepository.findAll().size();

        // Update the alarm
        Alarm updatedAlarm = alarmRepository.findById(alarm.getId()).get();
        // Disconnect from session so that the updates on updatedAlarm are not directly saved in db
        em.detach(updatedAlarm);
        updatedAlarm
            .message(UPDATED_MESSAGE)
            .timestamp(UPDATED_TIMESTAMP)
            .activated(UPDATED_ACTIVATED)
            .repeatCount(UPDATED_REPEAT_COUNT)
            .firstTimestamp(UPDATED_FIRST_TIMESTAMP)
            .dismissed(UPDATED_DISMISSED)
            .context(UPDATED_CONTEXT);
        AlarmDTO alarmDTO = alarmMapper.toDto(updatedAlarm);

        restAlarmMockMvc.perform(put("/api/alarms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isOk());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeUpdate);
        Alarm testAlarm = alarmList.get(alarmList.size() - 1);
        assertThat(testAlarm.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testAlarm.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testAlarm.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testAlarm.getRepeatCount()).isEqualTo(UPDATED_REPEAT_COUNT);
        assertThat(testAlarm.getFirstTimestamp()).isEqualTo(UPDATED_FIRST_TIMESTAMP);
        assertThat(testAlarm.isDismissed()).isEqualTo(UPDATED_DISMISSED);
        assertThat(testAlarm.getContext()).isEqualTo(UPDATED_CONTEXT);

        // Validate the Alarm in Elasticsearch
        verify(mockAlarmSearchRepository, times(1)).save(testAlarm);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarm() throws Exception {
        int databaseSizeBeforeUpdate = alarmRepository.findAll().size();

        // Create the Alarm
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmMockMvc.perform(put("/api/alarms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Alarm in Elasticsearch
        verify(mockAlarmSearchRepository, times(0)).save(alarm);
    }

    @Test
    @Transactional
    public void deleteAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        int databaseSizeBeforeDelete = alarmRepository.findAll().size();

        // Delete the alarm
        restAlarmMockMvc.perform(delete("/api/alarms/{id}", alarm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Alarm in Elasticsearch
        verify(mockAlarmSearchRepository, times(1)).deleteById(alarm.getId());
    }

    @Test
    @Transactional
    public void searchAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);
        when(mockAlarmSearchRepository.search(queryStringQuery("id:" + alarm.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(alarm), PageRequest.of(0, 1), 1));
        // Search the alarm
        restAlarmMockMvc.perform(get("/api/_search/alarms?query=id:" + alarm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarm.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].repeatCount").value(hasItem(DEFAULT_REPEAT_COUNT)))
            .andExpect(jsonPath("$.[*].firstTimestamp").value(hasItem(DEFAULT_FIRST_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].dismissed").value(hasItem(DEFAULT_DISMISSED.booleanValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alarm.class);
        Alarm alarm1 = new Alarm();
        alarm1.setId(1L);
        Alarm alarm2 = new Alarm();
        alarm2.setId(alarm1.getId());
        assertThat(alarm1).isEqualTo(alarm2);
        alarm2.setId(2L);
        assertThat(alarm1).isNotEqualTo(alarm2);
        alarm1.setId(null);
        assertThat(alarm1).isNotEqualTo(alarm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmDTO.class);
        AlarmDTO alarmDTO1 = new AlarmDTO();
        alarmDTO1.setId(1L);
        AlarmDTO alarmDTO2 = new AlarmDTO();
        assertThat(alarmDTO1).isNotEqualTo(alarmDTO2);
        alarmDTO2.setId(alarmDTO1.getId());
        assertThat(alarmDTO1).isEqualTo(alarmDTO2);
        alarmDTO2.setId(2L);
        assertThat(alarmDTO1).isNotEqualTo(alarmDTO2);
        alarmDTO1.setId(null);
        assertThat(alarmDTO1).isNotEqualTo(alarmDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(alarmMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(alarmMapper.fromId(null)).isNull();
    }
}
