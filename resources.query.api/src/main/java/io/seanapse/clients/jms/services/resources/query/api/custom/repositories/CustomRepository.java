package io.seanapse.clients.jms.services.resources.query.api.custom.repositories;

import io.seanapse.clients.jms.services.resources.core.custom.models.Custom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRepository extends MongoRepository<Custom, String> {

    @Query(value = "{'customOffice': {$regex: '?0', $options: 'i'}}")
    List<Custom> findByFilterRegex(String filter);

    @Query(value = "{ id: { $exists: true}}")
    List<Custom> findCustomsByPage(final Pageable pageable);
}
