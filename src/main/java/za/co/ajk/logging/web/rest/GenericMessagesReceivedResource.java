package za.co.ajk.logging.web.rest;

import com.codahale.metrics.annotation.Timed;
import za.co.ajk.logging.domain.GenericMessagesReceived;
import za.co.ajk.logging.service.GenericMessagesReceivedService;
import za.co.ajk.logging.web.rest.errors.BadRequestAlertException;
import za.co.ajk.logging.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GenericMessagesReceived.
 */
@RestController
@RequestMapping("/api")
public class GenericMessagesReceivedResource {

    private final Logger log = LoggerFactory.getLogger(GenericMessagesReceivedResource.class);

    private static final String ENTITY_NAME = "genericMessagesReceived";

    private final GenericMessagesReceivedService genericMessagesReceivedService;

    public GenericMessagesReceivedResource(GenericMessagesReceivedService genericMessagesReceivedService) {
        this.genericMessagesReceivedService = genericMessagesReceivedService;
    }

    /**
     * POST  /generic-messages-receiveds : Create a new genericMessagesReceived.
     *
     * @param genericMessagesReceived the genericMessagesReceived to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genericMessagesReceived, or with status 400 (Bad Request) if the genericMessagesReceived has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/generic-messages-receiveds")
    @Timed
    public ResponseEntity<GenericMessagesReceived> createGenericMessagesReceived(@Valid @RequestBody GenericMessagesReceived genericMessagesReceived) throws URISyntaxException {
        log.debug("REST request to save GenericMessagesReceived : {}", genericMessagesReceived);
        if (genericMessagesReceived.getId() != null) {
            throw new BadRequestAlertException("A new genericMessagesReceived cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenericMessagesReceived result = genericMessagesReceivedService.save(genericMessagesReceived);
        return ResponseEntity.created(new URI("/api/generic-messages-receiveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /generic-messages-receiveds : Updates an existing genericMessagesReceived.
     *
     * @param genericMessagesReceived the genericMessagesReceived to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genericMessagesReceived,
     * or with status 400 (Bad Request) if the genericMessagesReceived is not valid,
     * or with status 500 (Internal Server Error) if the genericMessagesReceived couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/generic-messages-receiveds")
    @Timed
    public ResponseEntity<GenericMessagesReceived> updateGenericMessagesReceived(@Valid @RequestBody GenericMessagesReceived genericMessagesReceived) throws URISyntaxException {
        log.debug("REST request to update GenericMessagesReceived : {}", genericMessagesReceived);
        if (genericMessagesReceived.getId() == null) {
            return createGenericMessagesReceived(genericMessagesReceived);
        }
        GenericMessagesReceived result = genericMessagesReceivedService.save(genericMessagesReceived);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, genericMessagesReceived.getId().toString()))
            .body(result);
    }

    /**
     * GET  /generic-messages-receiveds : get all the genericMessagesReceiveds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of genericMessagesReceiveds in body
     */
    @GetMapping("/generic-messages-receiveds")
    @Timed
    public List<GenericMessagesReceived> getAllGenericMessagesReceiveds() {
        log.debug("REST request to get all GenericMessagesReceiveds");
        return genericMessagesReceivedService.findAll();
        }

    /**
     * GET  /generic-messages-receiveds/:id : get the "id" genericMessagesReceived.
     *
     * @param id the id of the genericMessagesReceived to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genericMessagesReceived, or with status 404 (Not Found)
     */
    @GetMapping("/generic-messages-receiveds/{id}")
    @Timed
    public ResponseEntity<GenericMessagesReceived> getGenericMessagesReceived(@PathVariable String id) {
        log.debug("REST request to get GenericMessagesReceived : {}", id);
        GenericMessagesReceived genericMessagesReceived = genericMessagesReceivedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(genericMessagesReceived));
    }

    /**
     * DELETE  /generic-messages-receiveds/:id : delete the "id" genericMessagesReceived.
     *
     * @param id the id of the genericMessagesReceived to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/generic-messages-receiveds/{id}")
    @Timed
    public ResponseEntity<Void> deleteGenericMessagesReceived(@PathVariable String id) {
        log.debug("REST request to delete GenericMessagesReceived : {}", id);
        genericMessagesReceivedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/generic-messages-receiveds?query=:query : search for the genericMessagesReceived corresponding
     * to the query.
     *
     * @param query the query of the genericMessagesReceived search
     * @return the result of the search
     */
    @GetMapping("/_search/generic-messages-receiveds")
    @Timed
    public List<GenericMessagesReceived> searchGenericMessagesReceiveds(@RequestParam String query) {
        log.debug("REST request to search GenericMessagesReceiveds for query {}", query);
        return genericMessagesReceivedService.search(query);
    }

}
