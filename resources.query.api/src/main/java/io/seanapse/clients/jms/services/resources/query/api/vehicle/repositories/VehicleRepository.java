package io.seanapse.clients.jms.services.resources.query.api.vehicle.repositories;

import io.seanapse.clients.jms.services.resources.core.vehicles.model.Vehicle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    @Query("{ id: { $exists: true }}")
    List<Vehicle> findVehiclesByPage(final Pageable pageable);

    @Query(value = "{'$or': [{'identificationPlate': {$regex: '?0', $options: 'i'}}, {'year': {$regex: '?0', $options: 'i'}}, {'make': {$regex: '?0', $options: 'i'}}, {'model': {$regex: '?0', $options: 'i'}}, {'color': {$regex: '?0', $options: 'i'}}]}")
    List<Vehicle> findByFilterRegex(String filter);

    List<Vehicle> findVehiclesByActiveIsTrue();

    List<Vehicle> findVehiclesByActiveIsTrue(Pageable pageable);

    long countByActiveIsTrue();
}
