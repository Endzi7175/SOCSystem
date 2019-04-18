package com.team20.siemcenter.service;

import com.team20.siemcenter.service.dto.AlarmDefinitionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AlarmDefinition.
 */
public interface AlarmDefinitionService {

    /**
     * Save a alarmDefinition.
     *
     * @param alarmDefinitionDTO the entity to save
     * @return the persisted entity
     */
    AlarmDefinitionDTO save(AlarmDefinitionDTO alarmDefinitionDTO);

    /**
     * Get all the alarmDefinitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AlarmDefinitionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alarmDefinition.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AlarmDefinitionDTO> findOne(Long id);

    /**
     * Delete the "id" alarmDefinition.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the alarmDefinition corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AlarmDefinitionDTO> search(String query, Pageable pageable);
}
