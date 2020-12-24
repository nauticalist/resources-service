package io.seanapse.clients.jms.services.resources.query.api.vessel.repositories;

import io.seanapse.clients.jms.services.resources.core.vessel.models.Vessel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VesselRepository extends MongoRepository<Vessel, String> {
    @Query("{ id: { $exists: true }}")
    List<Vessel> findVessels(final Pageable pageable);

    @Query(value = "{'$or': [{'vesselName': {$regex: '?0', $options: 'i'}}, {'vesselType': {$regex: '?0', $options: 'i'}}, {'flag': {$regex: '?0', $options: 'i'}}, {'builtYear': {$regex: '?0', $options: 'i'}}, {'grossTonnage': {$regex: '?0', $options: 'i'}}, {'imoNumber': {$regex: '?0', $options: 'i'}}]}")
    List<Vessel> findByFilterRegex(String filter);

    List<Vessel> findVesselsByIsActiveIsTrue();

    List<Vessel> findVesselsByIsActiveIsTrue(final Pageable pageable);

    long countByIsActiveIsTrue();
}
