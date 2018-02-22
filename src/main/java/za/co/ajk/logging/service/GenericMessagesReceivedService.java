package za.co.ajk.logging.service;

import za.co.ajk.logging.domain.GenericMessagesReceived;
import za.co.ajk.logging.repository.GenericMessagesReceivedRepository;
import za.co.ajk.logging.repository.search.GenericMessagesReceivedSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GenericMessagesReceived.
 */
@Service
public class GenericMessagesReceivedService {

    private final Logger log = LoggerFactory.getLogger(GenericMessagesReceivedService.class);

    private final GenericMessagesReceivedRepository genericMessagesReceivedRepository;

    private final GenericMessagesReceivedSearchRepository genericMessagesReceivedSearchRepository;

    public GenericMessagesReceivedService(GenericMessagesReceivedRepository genericMessagesReceivedRepository, GenericMessagesReceivedSearchRepository genericMessagesReceivedSearchRepository) {
        this.genericMessagesReceivedRepository = genericMessagesReceivedRepository;
        this.genericMessagesReceivedSearchRepository = genericMessagesReceivedSearchRepository;
    }

    /**
     * Save a genericMessagesReceived.
     *
     * @param genericMessagesReceived the entity to save
     * @return the persisted entity
     */
    public GenericMessagesReceived save(GenericMessagesReceived genericMessagesReceived) {
        log.debug("Request to save GenericMessagesReceived : {}", genericMessagesReceived);
        GenericMessagesReceived result = genericMessagesReceivedRepository.save(genericMessagesReceived);
        genericMessagesReceivedSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the genericMessagesReceiveds.
     *
     * @return the list of entities
     */
    public List<GenericMessagesReceived> findAll() {
        log.debug("Request to get all GenericMessagesReceiveds");
        return genericMessagesReceivedRepository.findAll();
    }

    /**
     * Get one genericMessagesReceived by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public GenericMessagesReceived findOne(String id) {
        log.debug("Request to get GenericMessagesReceived : {}", id);
        return genericMessagesReceivedRepository.findOne(id);
    }

    /**
     * Delete the genericMessagesReceived by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete GenericMessagesReceived : {}", id);
        genericMessagesReceivedRepository.delete(id);
        genericMessagesReceivedSearchRepository.delete(id);
    }

    /**
     * Search for the genericMessagesReceived corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    public List<GenericMessagesReceived> search(String query) {
        log.debug("Request to search GenericMessagesReceiveds for query {}", query);
        return StreamSupport
            .stream(genericMessagesReceivedSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
