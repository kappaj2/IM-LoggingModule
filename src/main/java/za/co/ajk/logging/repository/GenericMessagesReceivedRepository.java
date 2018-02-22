package za.co.ajk.logging.repository;

import za.co.ajk.logging.domain.GenericMessagesReceived;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the GenericMessagesReceived entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenericMessagesReceivedRepository extends MongoRepository<GenericMessagesReceived, String> {

}
