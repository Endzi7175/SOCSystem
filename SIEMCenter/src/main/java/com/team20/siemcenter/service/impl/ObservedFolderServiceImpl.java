package com.team20.siemcenter.service.impl;

import com.team20.siemcenter.service.ObservedFolderService;
import com.team20.siemcenter.domain.ObservedFolder;
import com.team20.siemcenter.repository.ObservedFolderRepository;
import com.team20.siemcenter.repository.search.ObservedFolderSearchRepository;
import com.team20.siemcenter.service.dto.ObservedFolderDTO;
import com.team20.siemcenter.service.mapper.ObservedFolderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ObservedFolder.
 */
@Service
@Transactional
public class ObservedFolderServiceImpl implements ObservedFolderService {

    private final Logger log = LoggerFactory.getLogger(ObservedFolderServiceImpl.class);

    private final ObservedFolderRepository observedFolderRepository;

    private final ObservedFolderMapper observedFolderMapper;

    private final ObservedFolderSearchRepository observedFolderSearchRepository;

    public ObservedFolderServiceImpl(ObservedFolderRepository observedFolderRepository, ObservedFolderMapper observedFolderMapper, ObservedFolderSearchRepository observedFolderSearchRepository) {
        this.observedFolderRepository = observedFolderRepository;
        this.observedFolderMapper = observedFolderMapper;
        this.observedFolderSearchRepository = observedFolderSearchRepository;
    }

    /**
     * Save a observedFolder.
     *
     * @param observedFolderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ObservedFolderDTO save(ObservedFolderDTO observedFolderDTO) {
        log.debug("Request to save ObservedFolder : {}", observedFolderDTO);
        ObservedFolder observedFolder = observedFolderMapper.toEntity(observedFolderDTO);
        observedFolder = observedFolderRepository.save(observedFolder);
        ObservedFolderDTO result = observedFolderMapper.toDto(observedFolder);
        observedFolderSearchRepository.save(observedFolder);
        return result;
    }

    /**
     * Get all the observedFolders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObservedFolderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ObservedFolders");
        return observedFolderRepository.findAll(pageable)
            .map(observedFolderMapper::toDto);
    }

    /**
     * Get all the ObservedFolder with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ObservedFolderDTO> findAllWithEagerRelationships(Pageable pageable) {
        return observedFolderRepository.findAllWithEagerRelationships(pageable).map(observedFolderMapper::toDto);
    }
    

    /**
     * Get one observedFolder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ObservedFolderDTO> findOne(Long id) {
        log.debug("Request to get ObservedFolder : {}", id);
        return observedFolderRepository.findOneWithEagerRelationships(id)
            .map(observedFolderMapper::toDto);
    }

    /**
     * Delete the observedFolder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ObservedFolder : {}", id);
        observedFolderRepository.deleteById(id);
        observedFolderSearchRepository.deleteById(id);
    }

    /**
     * Search for the observedFolder corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ObservedFolderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ObservedFolders for query {}", query);
        return observedFolderSearchRepository.search(queryStringQuery(query), pageable)
            .map(observedFolderMapper::toDto);
    }
}
