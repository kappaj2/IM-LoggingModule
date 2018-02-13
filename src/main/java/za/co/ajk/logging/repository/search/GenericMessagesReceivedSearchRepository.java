package za.co.ajk.logging.repository.search;

import za.co.ajk.logging.domain.GenericMessagesReceived;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GenericMessagesReceived entity.
 */
public interface GenericMessagesReceivedSearchRepository extends ElasticsearchRepository<GenericMessagesReceived, String> {
}
