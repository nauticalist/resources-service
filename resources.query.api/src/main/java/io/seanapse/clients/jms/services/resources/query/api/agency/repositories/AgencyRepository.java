package io.seanapse.clients.jms.services.resources.query.api.agency.repositories;

import io.seanapse.clients.jms.services.resources.core.agency.models.Agency;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyRepository extends MongoRepository<Agency, String> {
    @Query(value = "{'$or': [{'agencyName': {$regex: '?0', $options: 'i'}}, {'companyTitle': {$regex: '?0', $options: 'i'}}, {'emailAddress': {$regex: '?0', $options: 'i'}}, {'address.country': {$regex: '?0', $options: 'i'}}, {'address.region': {$regex: '?0', $options: 'i'}}, {'address.city': {$regex: '?0', $options: 'i'}}, {'address.addressLine1': {$regex: '?0', $options: 'i'}}, {'address.addressLine2': {$regex: '?0', $options: 'i'}}]}")
    List<Agency> findByFilterRegex(String filter);

    @Query("{ id: { $exists: true }}")
    List<Agency> findAgencyByPage(final Pageable page);

    List<Agency> findAgenciesByActiveIsTrue(final Pageable page);

    List<Agency> findAgenciesByActiveIsTrue();

    long countByActiveIsTrue();
}
