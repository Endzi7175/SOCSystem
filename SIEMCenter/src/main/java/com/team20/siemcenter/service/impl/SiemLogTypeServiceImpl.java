package com.team20.siemcenter.service.impl;

import com.team20.siemcenter.service.SiemLogTypeService;
import com.team20.siemcenter.domain.SiemLogType;
import com.team20.siemcenter.repository.SiemLogTypeRepository;
import com.team20.siemcenter.repository.search.SiemLogTypeSearchRepository;
import com.team20.siemcenter.service.dto.SiemLogTypeDTO;
import com.team20.siemcenter.service.mapper.SiemLogTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SiemLogType.
 */
@Service
@Transactional
public class SiemLogTypeServiceImpl implements SiemLogTypeService {

    private final Logger log = LoggerFactory.getLogger(SiemLogTypeServiceImpl.class);

    private final SiemLogTypeRepository siemLogTypeRepository;

    private final SiemLogTypeMapper siemLogTypeMapper;

    private final SiemLogTypeSearchRepository siemLogTypeSearchRepository;

    public SiemLogTypeServiceImpl(SiemLogTypeRepository siemLogTypeRepository, SiemLogTypeMapper siemLogTypeMapper, SiemLogTypeSearchRepository siemLogTypeSearchRepository) {
        this.siemLogTypeRepository = siemLogTypeRepository;
        this.siemLogTypeMapper = siemLogTypeMapper;
        this.siemLogTypeSearchRepository = siemLogTypeSearchRepository;
    }

    /**
     * Save a siemLogType.
     *
     * @param siemLogTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SiemLogTypeDTO save(SiemLogTypeDTO siemLogTypeDTO) {
        log.debug("Request to save SiemLogType : {}", siemLogTypeDTO);
        SiemLogType siemLogType = siemLogTypeMapper.toEntity(siemLogTypeDTO);
        siemLogType = siemLogTypeRepository.save(siemLogType);
        SiemLogTypeDTO result = siemLogTypeMapper.toDto(siemLogType);
        siemLogTypeSearchRepository.save(siemLogType);
        return result;
    }

    /**
     * Get all the siemLogTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiemLogTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiemLogTypes");
        return siemLogTypeRepository.findAll(pageable)
            .map(siemLogTypeMapper::toDto);
    }


    /**
     * Get one siemLogType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SiemLogTypeDTO> findOne(Long id) {
        log.debug("Request to get SiemLogType : {}", id);
        return siemLogTypeRepository.findById(id)
            .map(siemLogTypeMapper::toDto);
    }

    /**
     * Delete the siemLogType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SiemLogType : {}", id);
        siemLogTypeRepository.deleteById(id);
        siemLogTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the siemLogType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiemLogTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SiemLogTypes for query {}", query);
        return siemLogTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(siemLogTypeMapper::toDto);
    }
}
