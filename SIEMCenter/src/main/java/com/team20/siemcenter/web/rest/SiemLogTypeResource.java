package com.team20.siemcenter.web.rest;
import com.team20.siemcenter.service.SiemLogTypeService;
import com.team20.siemcenter.web.rest.errors.BadRequestAlertException;
import com.team20.siemcenter.web.rest.util.HeaderUtil;
import com.team20.siemcenter.web.rest.util.PaginationUtil;
import com.team20.siemcenter.service.dto.SiemLogTypeDTO;
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
 * REST controller for managing SiemLogType.
 */
@RestController
@RequestMapping("/api")
public class SiemLogTypeResource {

    private final Logger log = LoggerFactory.getLogger(SiemLogTypeResource.class);

    private static final String ENTITY_NAME = "siemcenterSiemLogType";

    private final SiemLogTypeService siemLogTypeService;

    public SiemLogTypeResource(SiemLogTypeService siemLogTypeService) {
        this.siemLogTypeService = siemLogTypeService;
    }

    /**
     * POST  /siem-log-types : Create a new siemLogType.
     *
     * @param siemLogTypeDTO the siemLogTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new siemLogTypeDTO, or with status 400 (Bad Request) if the siemLogType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/siem-log-types")
    public ResponseEntity<SiemLogTypeDTO> createSiemLogType(@RequestBody SiemLogTypeDTO siemLogTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SiemLogType : {}", siemLogTypeDTO);
        if (siemLogTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new siemLogType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiemLogTypeDTO result = siemLogTypeService.save(siemLogTypeDTO);
        return ResponseEntity.created(new URI("/api/siem-log-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /siem-log-types : Updates an existing siemLogType.
     *
     * @param siemLogTypeDTO the siemLogTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siemLogTypeDTO,
     * or with status 400 (Bad Request) if the siemLogTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the siemLogTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/siem-log-types")
    public ResponseEntity<SiemLogTypeDTO> updateSiemLogType(@RequestBody SiemLogTypeDTO siemLogTypeDTO) throws URISyntaxException {
        log.debug("REST request to update SiemLogType : {}", siemLogTypeDTO);
        if (siemLogTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SiemLogTypeDTO result = siemLogTypeService.save(siemLogTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, siemLogTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /siem-log-types : get all the siemLogTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of siemLogTypes in body
     */
    @GetMapping("/siem-log-types")
    public ResponseEntity<List<SiemLogTypeDTO>> getAllSiemLogTypes(Pageable pageable) {
        log.debug("REST request to get a page of SiemLogTypes");
        Page<SiemLogTypeDTO> page = siemLogTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/siem-log-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /siem-log-types/:id : get the "id" siemLogType.
     *
     * @param id the id of the siemLogTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siemLogTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/siem-log-types/{id}")
    public ResponseEntity<SiemLogTypeDTO> getSiemLogType(@PathVariable Long id) {
        log.debug("REST request to get SiemLogType : {}", id);
        Optional<SiemLogTypeDTO> siemLogTypeDTO = siemLogTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siemLogTypeDTO);
    }

    /**
     * DELETE  /siem-log-types/:id : delete the "id" siemLogType.
     *
     * @param id the id of the siemLogTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/siem-log-types/{id}")
    public ResponseEntity<Void> deleteSiemLogType(@PathVariable Long id) {
        log.debug("REST request to delete SiemLogType : {}", id);
        siemLogTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/siem-log-types?query=:query : search for the siemLogType corresponding
     * to the query.
     *
     * @param query the query of the siemLogType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/siem-log-types")
    public ResponseEntity<List<SiemLogTypeDTO>> searchSiemLogTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SiemLogTypes for query {}", query);
        Page<SiemLogTypeDTO> page = siemLogTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/siem-log-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
