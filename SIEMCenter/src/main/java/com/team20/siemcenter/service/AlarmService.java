package com.team20.siemcenter.service;

import com.team20.siemcenter.service.dto.AlarmDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Alarm.
 */
public interface AlarmService {

    /**
     * Save a alarm.
     *
     * @param alarmDTO the entity to save
     * @return the persisted entity
     */
    AlarmDTO save(AlarmDTO alarmDTO);

    /**
     * Get all the alarms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AlarmDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alarm.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AlarmDTO> findOne(Long id);

    /**
     * Delete the "id" alarm.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the alarm corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AlarmDTO> search(String query, Pageable pageable);
}
