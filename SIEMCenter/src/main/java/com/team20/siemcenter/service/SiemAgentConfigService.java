package com.team20.siemcenter.service;

import com.team20.siemcenter.service.dto.SiemAgentConfigDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SiemAgentConfig.
 */
public interface SiemAgentConfigService {

    /**
     * Save a siemAgentConfig.
     *
     * @param siemAgentConfigDTO the entity to save
     * @return the persisted entity
     */
    SiemAgentConfigDTO save(SiemAgentConfigDTO siemAgentConfigDTO);

    /**
     * Get all the siemAgentConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiemAgentConfigDTO> findAll(Pageable pageable);


    /**
     * Get the "id" siemAgentConfig.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SiemAgentConfigDTO> findOne(Long id);

    /**
     * Delete the "id" siemAgentConfig.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the siemAgentConfig corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiemAgentConfigDTO> search(String query, Pageable pageable);
}
