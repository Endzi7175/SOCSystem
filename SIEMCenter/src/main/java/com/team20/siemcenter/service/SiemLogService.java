package com.team20.siemcenter.service;

import com.team20.siemcenter.service.dto.SiemLogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SiemLog.
 */
public interface SiemLogService {

    /**
     * Save a siemLog.
     *
     * @param siemLogDTO the entity to save
     * @return the persisted entity
     */
    SiemLogDTO save(SiemLogDTO siemLogDTO);

    /**
     * Get all the siemLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiemLogDTO> findAll(Pageable pageable);


    /**
     * Get the "id" siemLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SiemLogDTO> findOne(Long id);

    /**
     * Delete the "id" siemLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the siemLog corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiemLogDTO> search(String query, Pageable pageable);
}
