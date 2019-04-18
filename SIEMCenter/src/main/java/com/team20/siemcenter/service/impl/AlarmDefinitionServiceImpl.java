package com.team20.siemcenter.service.impl;

import com.team20.siemcenter.service.AlarmDefinitionService;
import com.team20.siemcenter.domain.AlarmDefinition;
import com.team20.siemcenter.repository.AlarmDefinitionRepository;
import com.team20.siemcenter.repository.search.AlarmDefinitionSearchRepository;
import com.team20.siemcenter.service.dto.AlarmDefinitionDTO;
import com.team20.siemcenter.service.mapper.AlarmDefinitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AlarmDefinition.
 */
@Service
@Transactional
public class AlarmDefinitionServiceImpl implements AlarmDefinitionService {

    private final Logger log = LoggerFactory.getLogger(AlarmDefinitionServiceImpl.class);

    private final AlarmDefinitionRepository alarmDefinitionRepository;

    private final AlarmDefinitionMapper alarmDefinitionMapper;

    private final AlarmDefinitionSearchRepository alarmDefinitionSearchRepository;

    public AlarmDefinitionServiceImpl(AlarmDefinitionRepository alarmDefinitionRepository, AlarmDefinitionMapper alarmDefinitionMapper, AlarmDefinitionSearchRepository alarmDefinitionSearchRepository) {
        this.alarmDefinitionRepository = alarmDefinitionRepository;
        this.alarmDefinitionMapper = alarmDefinitionMapper;
        this.alarmDefinitionSearchRepository = alarmDefinitionSearchRepository;
    }

    /**
     * Save a alarmDefinition.
     *
     * @param alarmDefinitionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AlarmDefinitionDTO save(AlarmDefinitionDTO alarmDefinitionDTO) {
        log.debug("Request to save AlarmDefinition : {}", alarmDefinitionDTO);
        AlarmDefinition alarmDefinition = alarmDefinitionMapper.toEntity(alarmDefinitionDTO);
        alarmDefinition = alarmDefinitionRepository.save(alarmDefinition);
        AlarmDefinitionDTO result = alarmDefinitionMapper.toDto(alarmDefinition);
        alarmDefinitionSearchRepository.save(alarmDefinition);
        return result;
    }

    /**
     * Get all the alarmDefinitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlarmDefinitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlarmDefinitions");
        return alarmDefinitionRepository.findAll(pageable)
            .map(alarmDefinitionMapper::toDto);
    }


    /**
     * Get one alarmDefinition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlarmDefinitionDTO> findOne(Long id) {
        log.debug("Request to get AlarmDefinition : {}", id);
        return alarmDefinitionRepository.findById(id)
            .map(alarmDefinitionMapper::toDto);
    }

    /**
     * Delete the alarmDefinition by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlarmDefinition : {}", id);
        alarmDefinitionRepository.deleteById(id);
        alarmDefinitionSearchRepository.deleteById(id);
    }

    /**
     * Search for the alarmDefinition corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AlarmDefinitionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AlarmDefinitions for query {}", query);
        return alarmDefinitionSearchRepository.search(queryStringQuery(query), pageable)
            .map(alarmDefinitionMapper::toDto);
    }
}
