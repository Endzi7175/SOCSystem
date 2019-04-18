package com.team20.siemcenter.service.impl;

import com.team20.siemcenter.service.SiemAgentConfigService;
import com.team20.siemcenter.domain.SiemAgentConfig;
import com.team20.siemcenter.repository.SiemAgentConfigRepository;
import com.team20.siemcenter.repository.search.SiemAgentConfigSearchRepository;
import com.team20.siemcenter.service.dto.SiemAgentConfigDTO;
import com.team20.siemcenter.service.mapper.SiemAgentConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SiemAgentConfig.
 */
@Service
@Transactional
public class SiemAgentConfigServiceImpl implements SiemAgentConfigService {

    private final Logger log = LoggerFactory.getLogger(SiemAgentConfigServiceImpl.class);

    private final SiemAgentConfigRepository siemAgentConfigRepository;

    private final SiemAgentConfigMapper siemAgentConfigMapper;

    private final SiemAgentConfigSearchRepository siemAgentConfigSearchRepository;

    public SiemAgentConfigServiceImpl(SiemAgentConfigRepository siemAgentConfigRepository, SiemAgentConfigMapper siemAgentConfigMapper, SiemAgentConfigSearchRepository siemAgentConfigSearchRepository) {
        this.siemAgentConfigRepository = siemAgentConfigRepository;
        this.siemAgentConfigMapper = siemAgentConfigMapper;
        this.siemAgentConfigSearchRepository = siemAgentConfigSearchRepository;
    }

    /**
     * Save a siemAgentConfig.
     *
     * @param siemAgentConfigDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SiemAgentConfigDTO save(SiemAgentConfigDTO siemAgentConfigDTO) {
        log.debug("Request to save SiemAgentConfig : {}", siemAgentConfigDTO);
        SiemAgentConfig siemAgentConfig = siemAgentConfigMapper.toEntity(siemAgentConfigDTO);
        siemAgentConfig = siemAgentConfigRepository.save(siemAgentConfig);
        SiemAgentConfigDTO result = siemAgentConfigMapper.toDto(siemAgentConfig);
        siemAgentConfigSearchRepository.save(siemAgentConfig);
        return result;
    }

    /**
     * Get all the siemAgentConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiemAgentConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiemAgentConfigs");
        return siemAgentConfigRepository.findAll(pageable)
            .map(siemAgentConfigMapper::toDto);
    }


    /**
     * Get one siemAgentConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SiemAgentConfigDTO> findOne(Long id) {
        log.debug("Request to get SiemAgentConfig : {}", id);
        return siemAgentConfigRepository.findById(id)
            .map(siemAgentConfigMapper::toDto);
    }

    /**
     * Delete the siemAgentConfig by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SiemAgentConfig : {}", id);
        siemAgentConfigRepository.deleteById(id);
        siemAgentConfigSearchRepository.deleteById(id);
    }

    /**
     * Search for the siemAgentConfig corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiemAgentConfigDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SiemAgentConfigs for query {}", query);
        return siemAgentConfigSearchRepository.search(queryStringQuery(query), pageable)
            .map(siemAgentConfigMapper::toDto);
    }
}
