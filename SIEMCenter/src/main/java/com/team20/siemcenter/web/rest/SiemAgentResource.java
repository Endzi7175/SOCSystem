package com.team20.siemcenter.web.rest;
import com.team20.siemcenter.service.SiemAgentService;
import com.team20.siemcenter.web.rest.errors.BadRequestAlertException;
import com.team20.siemcenter.web.rest.util.HeaderUtil;
import com.team20.siemcenter.web.rest.util.PaginationUtil;
import com.team20.siemcenter.service.dto.SiemAgentDTO;
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
 * REST controller for managing SiemAgent.
 */
@RestController
@RequestMapping("/api")
public class SiemAgentResource {

    private final Logger log = LoggerFactory.getLogger(SiemAgentResource.class);

    private static final String ENTITY_NAME = "siemcenterSiemAgent";

    private final SiemAgentService siemAgentService;

    public SiemAgentResource(SiemAgentService siemAgentService) {
        this.siemAgentService = siemAgentService;
    }

    /**
     * POST  /siem-agents : Create a new siemAgent.
     *
     * @param siemAgentDTO the siemAgentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new siemAgentDTO, or with status 400 (Bad Request) if the siemAgent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/siem-agents")
    public ResponseEntity<SiemAgentDTO> createSiemAgent(@RequestBody SiemAgentDTO siemAgentDTO) throws URISyntaxException {
        log.debug("REST request to save SiemAgent : {}", siemAgentDTO);
        if (siemAgentDTO.getId() != null) {
            throw new BadRequestAlertException("A new siemAgent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiemAgentDTO result = siemAgentService.save(siemAgentDTO);
        return ResponseEntity.created(new URI("/api/siem-agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /siem-agents : Updates an existing siemAgent.
     *
     * @param siemAgentDTO the siemAgentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siemAgentDTO,
     * or with status 400 (Bad Request) if the siemAgentDTO is not valid,
     * or with status 500 (Internal Server Error) if the siemAgentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/siem-agents")
    public ResponseEntity<SiemAgentDTO> updateSiemAgent(@RequestBody SiemAgentDTO siemAgentDTO) throws URISyntaxException {
        log.debug("REST request to update SiemAgent : {}", siemAgentDTO);
        if (siemAgentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SiemAgentDTO result = siemAgentService.save(siemAgentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, siemAgentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /siem-agents : get all the siemAgents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of siemAgents in body
     */
    @GetMapping("/siem-agents")
    public ResponseEntity<List<SiemAgentDTO>> getAllSiemAgents(Pageable pageable) {
        log.debug("REST request to get a page of SiemAgents");
        Page<SiemAgentDTO> page = siemAgentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/siem-agents");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /siem-agents/:id : get the "id" siemAgent.
     *
     * @param id the id of the siemAgentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siemAgentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/siem-agents/{id}")
    public ResponseEntity<SiemAgentDTO> getSiemAgent(@PathVariable Long id) {
        log.debug("REST request to get SiemAgent : {}", id);
        Optional<SiemAgentDTO> siemAgentDTO = siemAgentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siemAgentDTO);
    }

    /**
     * DELETE  /siem-agents/:id : delete the "id" siemAgent.
     *
     * @param id the id of the siemAgentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/siem-agents/{id}")
    public ResponseEntity<Void> deleteSiemAgent(@PathVariable Long id) {
        log.debug("REST request to delete SiemAgent : {}", id);
        siemAgentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/siem-agents?query=:query : search for the siemAgent corresponding
     * to the query.
     *
     * @param query the query of the siemAgent search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/siem-agents")
    public ResponseEntity<List<SiemAgentDTO>> searchSiemAgents(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SiemAgents for query {}", query);
        Page<SiemAgentDTO> page = siemAgentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/siem-agents");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
