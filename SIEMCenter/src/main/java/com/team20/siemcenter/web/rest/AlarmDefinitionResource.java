package com.team20.siemcenter.web.rest;
import com.team20.siemcenter.service.AlarmDefinitionService;
import com.team20.siemcenter.web.rest.errors.BadRequestAlertException;
import com.team20.siemcenter.web.rest.util.HeaderUtil;
import com.team20.siemcenter.web.rest.util.PaginationUtil;
import com.team20.siemcenter.service.dto.AlarmDefinitionDTO;
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
 * REST controller for managing AlarmDefinition.
 */
@RestController
@RequestMapping("/api")
public class AlarmDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(AlarmDefinitionResource.class);

    private static final String ENTITY_NAME = "siemcenterAlarmDefinition";

    private final AlarmDefinitionService alarmDefinitionService;

    public AlarmDefinitionResource(AlarmDefinitionService alarmDefinitionService) {
        this.alarmDefinitionService = alarmDefinitionService;
    }

    /**
     * POST  /alarm-definitions : Create a new alarmDefinition.
     *
     * @param alarmDefinitionDTO the alarmDefinitionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alarmDefinitionDTO, or with status 400 (Bad Request) if the alarmDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alarm-definitions")
    public ResponseEntity<AlarmDefinitionDTO> createAlarmDefinition(@RequestBody AlarmDefinitionDTO alarmDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save AlarmDefinition : {}", alarmDefinitionDTO);
        if (alarmDefinitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new alarmDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmDefinitionDTO result = alarmDefinitionService.save(alarmDefinitionDTO);
        return ResponseEntity.created(new URI("/api/alarm-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alarm-definitions : Updates an existing alarmDefinition.
     *
     * @param alarmDefinitionDTO the alarmDefinitionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alarmDefinitionDTO,
     * or with status 400 (Bad Request) if the alarmDefinitionDTO is not valid,
     * or with status 500 (Internal Server Error) if the alarmDefinitionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alarm-definitions")
    public ResponseEntity<AlarmDefinitionDTO> updateAlarmDefinition(@RequestBody AlarmDefinitionDTO alarmDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to update AlarmDefinition : {}", alarmDefinitionDTO);
        if (alarmDefinitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlarmDefinitionDTO result = alarmDefinitionService.save(alarmDefinitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alarmDefinitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alarm-definitions : get all the alarmDefinitions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of alarmDefinitions in body
     */
    @GetMapping("/alarm-definitions")
    public ResponseEntity<List<AlarmDefinitionDTO>> getAllAlarmDefinitions(Pageable pageable) {
        log.debug("REST request to get a page of AlarmDefinitions");
        Page<AlarmDefinitionDTO> page = alarmDefinitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/alarm-definitions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /alarm-definitions/:id : get the "id" alarmDefinition.
     *
     * @param id the id of the alarmDefinitionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alarmDefinitionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alarm-definitions/{id}")
    public ResponseEntity<AlarmDefinitionDTO> getAlarmDefinition(@PathVariable Long id) {
        log.debug("REST request to get AlarmDefinition : {}", id);
        Optional<AlarmDefinitionDTO> alarmDefinitionDTO = alarmDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alarmDefinitionDTO);
    }

    /**
     * DELETE  /alarm-definitions/:id : delete the "id" alarmDefinition.
     *
     * @param id the id of the alarmDefinitionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alarm-definitions/{id}")
    public ResponseEntity<Void> deleteAlarmDefinition(@PathVariable Long id) {
        log.debug("REST request to delete AlarmDefinition : {}", id);
        alarmDefinitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/alarm-definitions?query=:query : search for the alarmDefinition corresponding
     * to the query.
     *
     * @param query the query of the alarmDefinition search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/alarm-definitions")
    public ResponseEntity<List<AlarmDefinitionDTO>> searchAlarmDefinitions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AlarmDefinitions for query {}", query);
        Page<AlarmDefinitionDTO> page = alarmDefinitionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/alarm-definitions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
