package com.team20.siemcenter.service.impl;

import com.team20.siemcenter.service.AlarmService;
import com.team20.siemcenter.domain.Alarm;
import com.team20.siemcenter.repository.AlarmRepository;
import com.team20.siemcenter.repository.search.AlarmSearchRepository;
import com.team20.siemcenter.service.dto.AlarmDTO;
import com.team20.siemcenter.service.mapper.AlarmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Alarm.
 */
@Service
@Transactional
public class AlarmServiceImpl implements AlarmService {

    private final Logger log = LoggerFactory.getLogger(AlarmServiceImpl.class);

    private final AlarmRepository alarmRepository;

    private final AlarmMapper alarmMapper;

    private final AlarmSearchRepository alarmSearchRepository;

    public AlarmServiceImpl(AlarmRepository alarmRepository, AlarmMapper alarmMapper, AlarmSearchRepository alarmSearchRepository) {
        this.alarmRepository = alarmRepository;
        this.alarmMapper = alarmMapper;
        this.alarmSearchRepository = alarmSearchRepository;
    }

    /**
     * Save a alarm.
     *
     * @param alarmDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AlarmDTO save(AlarmDTO alarmDTO) {
        log.debug("Request to save Alarm : {}", alarmDTO);
        Alarm alarm = alarmMapper.toEntity(alarmDTO);
        alarm = alarmRepository.save(alarm);
        AlarmDTO result = alarmMapper.toDto(alarm);
        alarmSearchRepository.save(alarm);
        return result;
    }

    /**
     * Get all the alarms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlarmDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alarms");
        return alarmRepository.findAll(pageable)
            .map(alarmMapper::toDto);
    }


    /**
     * Get one alarm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlarmDTO> findOne(Long id) {
        log.debug("Request to get Alarm : {}", id);
        return alarmRepository.findById(id)
            .map(alarmMapper::toDto);
    }

    /**
     * Delete the alarm by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alarm : {}", id);
        alarmRepository.deleteById(id);
        alarmSearchRepository.deleteById(id);
    }

    /**
     * Search for the alarm corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlarmDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Alarms for query {}", query);
        return alarmSearchRepository.search(queryStringQuery(query), pageable)
            .map(alarmMapper::toDto);
    }
}
