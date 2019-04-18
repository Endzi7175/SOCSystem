package com.team20.siemcenter.service.impl;

import com.team20.siemcenter.service.SiemAgentService;
import com.team20.siemcenter.domain.SiemAgent;
import com.team20.siemcenter.repository.SiemAgentRepository;
import com.team20.siemcenter.repository.search.SiemAgentSearchRepository;
import com.team20.siemcenter.service.dto.SiemAgentDTO;
import com.team20.siemcenter.service.mapper.SiemAgentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SiemAgent.
 */
@Service
@Transactional
public class SiemAgentServiceImpl implements SiemAgentService {

    private final Logger log = LoggerFactory.getLogger(SiemAgentServiceImpl.class);

    private final SiemAgentRepository siemAgentRepository;

    private final SiemAgentMapper siemAgentMapper;

    private final SiemAgentSearchRepository siemAgentSearchRepository;

    public SiemAgentServiceImpl(SiemAgentRepository siemAgentRepository, SiemAgentMapper siemAgentMapper, SiemAgentSearchRepository siemAgentSearchRepository) {
        this.siemAgentRepository = siemAgentRepository;
        this.siemAgentMapper = siemAgentMapper;
        this.siemAgentSearchRepository = siemAgentSearchRepository;
    }

    /**
     * Save a siemAgent.
     *
     * @param siemAgentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SiemAgentDTO save(SiemAgentDTO siemAgentDTO) {
        log.debug("Request to save SiemAgent : {}", siemAgentDTO);
        SiemAgent siemAgent = siemAgentMapper.toEntity(siemAgentDTO);
        siemAgent = siemAgentRepository.save(siemAgent);
        SiemAgentDTO result = siemAgentMapper.toDto(siemAgent);
        siemAgentSearchRepository.save(siemAgent);
        return result;
    }

    /**
     * Get all the siemAgents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiemAgentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiemAgents");
        return siemAgentRepository.findAll(pageable)
            .map(siemAgentMapper::toDto);
    }


    /**
     * Get one siemAgent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SiemAgentDTO> findOne(Long id) {
        log.debug("Request to get SiemAgent : {}", id);
        return siemAgentRepository.findById(id)
            .map(siemAgentMapper::toDto);
    }

    /**
     * Delete the siemAgent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SiemAgent : {}", id);
        siemAgentRepository.deleteById(id);
        siemAgentSearchRepository.deleteById(id);
    }

    /**
     * Search for the siemAgent corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiemAgentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SiemAgents for query {}", query);
        return siemAgentSearchRepository.search(queryStringQuery(query), pageable)
            .map(siemAgentMapper::toDto);
    }
}
