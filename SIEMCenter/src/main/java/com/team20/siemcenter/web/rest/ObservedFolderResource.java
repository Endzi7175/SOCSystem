package com.team20.siemcenter.web.rest;
import com.team20.siemcenter.service.ObservedFolderService;
import com.team20.siemcenter.web.rest.errors.BadRequestAlertException;
import com.team20.siemcenter.web.rest.util.HeaderUtil;
import com.team20.siemcenter.web.rest.util.PaginationUtil;
import com.team20.siemcenter.service.dto.ObservedFolderDTO;
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
 * REST controller for managing ObservedFolder.
 */
@RestController
@RequestMapping("/api")
public class ObservedFolderResource {

    private final Logger log = LoggerFactory.getLogger(ObservedFolderResource.class);

    private static final String ENTITY_NAME = "siemcenterObservedFolder";

    private final ObservedFolderService observedFolderService;

    public ObservedFolderResource(ObservedFolderService observedFolderService) {
        this.observedFolderService = observedFolderService;
    }

    /**
     * POST  /observed-folders : Create a new observedFolder.
     *
     * @param observedFolderDTO the observedFolderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new observedFolderDTO, or with status 400 (Bad Request) if the observedFolder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/observed-folders")
    public ResponseEntity<ObservedFolderDTO> createObservedFolder(@RequestBody ObservedFolderDTO observedFolderDTO) throws URISyntaxException {
        log.debug("REST request to save ObservedFolder : {}", observedFolderDTO);
        if (observedFolderDTO.getId() != null) {
            throw new BadRequestAlertException("A new observedFolder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObservedFolderDTO result = observedFolderService.save(observedFolderDTO);
        return ResponseEntity.created(new URI("/api/observed-folders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /observed-folders : Updates an existing observedFolder.
     *
     * @param observedFolderDTO the observedFolderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated observedFolderDTO,
     * or with status 400 (Bad Request) if the observedFolderDTO is not valid,
     * or with status 500 (Internal Server Error) if the observedFolderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/observed-folders")
    public ResponseEntity<ObservedFolderDTO> updateObservedFolder(@RequestBody ObservedFolderDTO observedFolderDTO) throws URISyntaxException {
        log.debug("REST request to update ObservedFolder : {}", observedFolderDTO);
        if (observedFolderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObservedFolderDTO result = observedFolderService.save(observedFolderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, observedFolderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /observed-folders : get all the observedFolders.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of observedFolders in body
     */
    @GetMapping("/observed-folders")
    public ResponseEntity<List<ObservedFolderDTO>> getAllObservedFolders(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ObservedFolders");
        Page<ObservedFolderDTO> page;
        if (eagerload) {
            page = observedFolderService.findAllWithEagerRelationships(pageable);
        } else {
            page = observedFolderService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/observed-folders?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /observed-folders/:id : get the "id" observedFolder.
     *
     * @param id the id of the observedFolderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the observedFolderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/observed-folders/{id}")
    public ResponseEntity<ObservedFolderDTO> getObservedFolder(@PathVariable Long id) {
        log.debug("REST request to get ObservedFolder : {}", id);
        Optional<ObservedFolderDTO> observedFolderDTO = observedFolderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(observedFolderDTO);
    }

    /**
     * DELETE  /observed-folders/:id : delete the "id" observedFolder.
     *
     * @param id the id of the observedFolderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/observed-folders/{id}")
    public ResponseEntity<Void> deleteObservedFolder(@PathVariable Long id) {
        log.debug("REST request to delete ObservedFolder : {}", id);
        observedFolderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/observed-folders?query=:query : search for the observedFolder corresponding
     * to the query.
     *
     * @param query the query of the observedFolder search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/observed-folders")
    public ResponseEntity<List<ObservedFolderDTO>> searchObservedFolders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ObservedFolders for query {}", query);
        Page<ObservedFolderDTO> page = observedFolderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/observed-folders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
