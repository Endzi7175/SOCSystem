package com.team20.siemcenter.service;

import com.team20.siemcenter.service.dto.SiemAgentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SiemAgent.
 */
public interface SiemAgentService {

    /**
     * Save a siemAgent.
     *
     * @param siemAgentDTO the entity to save
     * @return the persisted entity
     */
    SiemAgentDTO save(SiemAgentDTO siemAgentDTO);

    /**
     * Get all the siemAgents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiemAgentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" siemAgent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SiemAgentDTO> findOne(Long id);

    /**
     * Delete the "id" siemAgent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the siemAgent corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiemAgentDTO> search(String query, Pageable pageable);
}
