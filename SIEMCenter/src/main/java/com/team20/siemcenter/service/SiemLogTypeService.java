package com.team20.siemcenter.service;

import com.team20.siemcenter.service.dto.SiemLogTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SiemLogType.
 */
public interface SiemLogTypeService {

    /**
     * Save a siemLogType.
     *
     * @param siemLogTypeDTO the entity to save
     * @return the persisted entity
     */
    SiemLogTypeDTO save(SiemLogTypeDTO siemLogTypeDTO);

    /**
     * Get all the siemLogTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiemLogTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" siemLogType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SiemLogTypeDTO> findOne(Long id);

    /**
     * Delete the "id" siemLogType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the siemLogType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SiemLogTypeDTO> search(String query, Pageable pageable);
}
