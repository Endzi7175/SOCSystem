package com.team20.siemcenter.web.rest;
import com.team20.siemcenter.service.SiemAgentConfigService;
import com.team20.siemcenter.web.rest.errors.BadRequestAlertException;
import com.team20.siemcenter.web.rest.util.HeaderUtil;
import com.team20.siemcenter.web.rest.util.PaginationUtil;
import com.team20.siemcenter.service.dto.SiemAgentConfigDTO;
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
 * REST controller for managing SiemAgentConfig.
 */
@RestController
@RequestMapping("/api")
public class SiemAgentConfigResource {

    private final Logger log = LoggerFactory.getLogger(SiemAgentConfigResource.class);

    private static final String ENTITY_NAME = "siemcenterSiemAgentConfig";

    private final SiemAgentConfigService siemAgentConfigService;

    public SiemAgentConfigResource(SiemAgentConfigService siemAgentConfigService) {
        this.siemAgentConfigService = siemAgentConfigService;
    }

    /**
     * POST  /siem-agent-configs : Create a new siemAgentConfig.
     *
     * @param siemAgentConfigDTO the siemAgentConfigDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new siemAgentConfigDTO, or with status 400 (Bad Request) if the siemAgentConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/siem-agent-configs")
    public ResponseEntity<SiemAgentConfigDTO> createSiemAgentConfig(@RequestBody SiemAgentConfigDTO siemAgentConfigDTO) throws URISyntaxException {
        log.debug("REST request to save SiemAgentConfig : {}", siemAgentConfigDTO);
        if (siemAgentConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new siemAgentConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiemAgentConfigDTO result = siemAgentConfigService.save(siemAgentConfigDTO);
        return ResponseEntity.created(new URI("/api/siem-agent-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /siem-agent-configs : Updates an existing siemAgentConfig.
     *
     * @param siemAgentConfigDTO the siemAgentConfigDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siemAgentConfigDTO,
     * or with status 400 (Bad Request) if the siemAgentConfigDTO is not valid,
     * or with status 500 (Internal Server Error) if the siemAgentConfigDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/siem-agent-configs")
    public ResponseEntity<SiemAgentConfigDTO> updateSiemAgentConfig(@RequestBody SiemAgentConfigDTO siemAgentConfigDTO) throws URISyntaxException {
        log.debug("REST request to update SiemAgentConfig : {}", siemAgentConfigDTO);
        if (siemAgentConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SiemAgentConfigDTO result = siemAgentConfigService.save(siemAgentConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, siemAgentConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /siem-agent-configs : get all the siemAgentConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of siemAgentConfigs in body
     */
    @GetMapping("/siem-agent-configs")
    public ResponseEntity<List<SiemAgentConfigDTO>> getAllSiemAgentConfigs(Pageable pageable) {
        log.debug("REST request to get a page of SiemAgentConfigs");
        Page<SiemAgentConfigDTO> page = siemAgentConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/siem-agent-configs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /siem-agent-configs/:id : get the "id" siemAgentConfig.
     *
     * @param id the id of the siemAgentConfigDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siemAgentConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/siem-agent-configs/{id}")
    public ResponseEntity<SiemAgentConfigDTO> getSiemAgentConfig(@PathVariable Long id) {
        log.debug("REST request to get SiemAgentConfig : {}", id);
        Optional<SiemAgentConfigDTO> siemAgentConfigDTO = siemAgentConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siemAgentConfigDTO);
    }

    /**
     * DELETE  /siem-agent-configs/:id : delete the "id" siemAgentConfig.
     *
     * @param id the id of the siemAgentConfigDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/siem-agent-configs/{id}")
    public ResponseEntity<Void> deleteSiemAgentConfig(@PathVariable Long id) {
        log.debug("REST request to delete SiemAgentConfig : {}", id);
        siemAgentConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/siem-agent-configs?query=:query : search for the siemAgentConfig corresponding
     * to the query.
     *
     * @param query the query of the siemAgentConfig search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/siem-agent-configs")
    public ResponseEntity<List<SiemAgentConfigDTO>> searchSiemAgentConfigs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SiemAgentConfigs for query {}", query);
        Page<SiemAgentConfigDTO> page = siemAgentConfigService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/siem-agent-configs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
