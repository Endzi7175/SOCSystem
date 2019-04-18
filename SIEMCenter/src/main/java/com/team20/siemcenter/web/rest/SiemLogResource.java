package com.team20.siemcenter.web.rest;
import com.team20.siemcenter.service.SiemLogService;
import com.team20.siemcenter.web.rest.errors.BadRequestAlertException;
import com.team20.siemcenter.web.rest.util.HeaderUtil;
import com.team20.siemcenter.web.rest.util.PaginationUtil;
import com.team20.siemcenter.service.dto.SiemLogDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SiemLog.
 */
@RestController
@RequestMapping("/api")
public class SiemLogResource {

    private final Logger log = LoggerFactory.getLogger(SiemLogResource.class);

    private static final String ENTITY_NAME = "siemcenterSiemLog";

    private final SiemLogService siemLogService;

    public SiemLogResource(SiemLogService siemLogService) {
        this.siemLogService = siemLogService;
    }

    /**
     * POST  /siem-logs : Create a new siemLog.
     *
     * @param siemLogDTO the siemLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new siemLogDTO, or with status 400 (Bad Request) if the siemLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/siem-logs")
    public ResponseEntity<SiemLogDTO> createSiemLog(@RequestBody SiemLogDTO siemLogDTO) throws URISyntaxException {
        log.debug("REST request to save SiemLog : {}", siemLogDTO);
        if (siemLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new siemLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiemLogDTO result = siemLogService.save(siemLogDTO);
        return ResponseEntity.created(new URI("/api/siem-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /siem-logs : Updates an existing siemLog.
     *
     * @param siemLogDTO the siemLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siemLogDTO,
     * or with status 400 (Bad Request) if the siemLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the siemLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/siem-logs")
    public ResponseEntity<SiemLogDTO> updateSiemLog(@RequestBody SiemLogDTO siemLogDTO) throws URISyntaxException {
        log.debug("REST request to update SiemLog : {}", siemLogDTO);
        if (siemLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SiemLogDTO result = siemLogService.save(siemLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, siemLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /siem-logs : get all the siemLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of siemLogs in body
     */
    @GetMapping("/siem-logs")
    public ResponseEntity<List<SiemLogDTO>> getAllSiemLogs(Pageable pageable) {
        log.debug("REST request to get a page of SiemLogs");
        Page<SiemLogDTO> page = siemLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/siem-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /siem-logs/:id : get the "id" siemLog.
     *
     * @param id the id of the siemLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siemLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/siem-logs/{id}")
    public ResponseEntity<SiemLogDTO> getSiemLog(@PathVariable Long id) {
        log.debug("REST request to get SiemLog : {}", id);
        Optional<SiemLogDTO> siemLogDTO = siemLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siemLogDTO);
    }

    /**
     * DELETE  /siem-logs/:id : delete the "id" siemLog.
     *
     * @param id the id of the siemLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/siem-logs/{id}")
    public ResponseEntity<Void> deleteSiemLog(@PathVariable Long id) {
        log.debug("REST request to delete SiemLog : {}", id);
        siemLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/siem-logs?query=:query : search for the siemLog corresponding
     * to the query.
     *
     * @param query the query of the siemLog search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/siem-logs")
    public ResponseEntity<List<SiemLogDTO>> searchSiemLogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SiemLogs for query {}", query);
        Page<SiemLogDTO> page = siemLogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/siem-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
