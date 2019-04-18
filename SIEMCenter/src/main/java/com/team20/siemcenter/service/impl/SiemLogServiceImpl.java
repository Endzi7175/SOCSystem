package com.team20.siemcenter.service.impl;

import com.team20.siemcenter.service.SiemLogService;
import com.team20.siemcenter.domain.SiemLog;
import com.team20.siemcenter.repository.SiemLogRepository;
import com.team20.siemcenter.repository.search.SiemLogSearchRepository;
import com.team20.siemcenter.service.dto.SiemLogDTO;
import com.team20.siemcenter.service.mapper.SiemLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SiemLog.
 */
@Service
@Transactional
public class SiemLogServiceImpl implements SiemLogService {

    private final Logger log = LoggerFactory.getLogger(SiemLogServiceImpl.class);

    private final SiemLogRepository siemLogRepository;

    private final SiemLogMapper siemLogMapper;

    private final SiemLogSearchRepository siemLogSearchRepository;

    public SiemLogServiceImpl(SiemLogRepository siemLogRepository, SiemLogMapper siemLogMapper, SiemLogSearchRepository siemLogSearchRepository) {
        this.siemLogRepository = siemLogRepository;
        this.siemLogMapper = siemLogMapper;
        this.siemLogSearchRepository = siemLogSearchRepository;
    }

    /**
     * Save a siemLog.
     *
     * @param siemLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SiemLogDTO save(SiemLogDTO siemLogDTO) {
        log.debug("Request to save SiemLog : {}", siemLogDTO);
        SiemLog siemLog = siemLogMapper.toEntity(siemLogDTO);
        siemLog = siemLogRepository.save(siemLog);
        SiemLogDTO result = siemLogMapper.toDto(siemLog);
        siemLogSearchRepository.save(siemLog);
        return result;
    }

    /**
     * Get all the siemLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiemLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiemLogs");
        return siemLogRepository.findAll(pageable)
            .map(siemLogMapper::toDto);
    }


    /**
     * Get one siemLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SiemLogDTO> findOne(Long id) {
        log.debug("Request to get SiemLog : {}", id);
        return siemLogRepository.findById(id)
            .map(siemLogMapper::toDto);
    }

    /**
     * Delete the siemLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SiemLog : {}", id);
        siemLogRepository.deleteById(id);
        siemLogSearchRepository.deleteById(id);
    }

    /**
     * Search for the siemLog corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SiemLogDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SiemLogs for query {}", query);
        return siemLogSearchRepository.search(queryStringQuery(query), pageable)
            .map(siemLogMapper::toDto);
    }
}
