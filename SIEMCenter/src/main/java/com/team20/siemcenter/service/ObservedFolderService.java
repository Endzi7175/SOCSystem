package com.team20.siemcenter.service;

import com.team20.siemcenter.service.dto.ObservedFolderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ObservedFolder.
 */
public interface ObservedFolderService {

    /**
     * Save a observedFolder.
     *
     * @param observedFolderDTO the entity to save
     * @return the persisted entity
     */
    ObservedFolderDTO save(ObservedFolderDTO observedFolderDTO);

    /**
     * Get all the observedFolders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ObservedFolderDTO> findAll(Pageable pageable);

    /**
     * Get all the ObservedFolder with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ObservedFolderDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" observedFolder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ObservedFolderDTO> findOne(Long id);

    /**
     * Delete the "id" observedFolder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the observedFolder corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ObservedFolderDTO> search(String query, Pageable pageable);
}
