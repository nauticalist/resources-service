package io.seanapse.clients.jms.services.resources.query.api.port.repositories;

import io.seanapse.clients.jms.services.resources.core.port.models.Port;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortRepository extends MongoRepository<Port, String> {
    @Query(value = "{'$or' : [{'portName': {$regex : '?0', $options: 'i'}}, {'location.country': {$regex : ?0, $options: 'i'}}, {'location.unLoCode': {$regex : ?0, $options: 'i'}}]}")
    List<Port> findByFilterRegex(String filter);

    @Query("{ id: { $exists: true }}")
    List<Port> findPortsByPage(final Pageable page);
}
