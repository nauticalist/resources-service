package io.seanapse.clients.jms.services.resources.query.api.agency.handlers.impl;

import io.seanapse.clients.jms.services.resources.query.api.agency.dto.AgencyResponse;
import io.seanapse.clients.jms.services.resources.query.api.agency.dto.PagedAgencyResponse;
import io.seanapse.clients.jms.services.resources.query.api.agency.handlers.AgencyQueryHandler;
import io.seanapse.clients.jms.services.resources.query.api.agency.queries.*;
import io.seanapse.clients.jms.services.resources.query.api.agency.repositories.AgencyRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AgencyQueryHandlerImpl implements AgencyQueryHandler {
    private final AgencyRepository agencyRepository;

    @Autowired
    public AgencyQueryHandlerImpl(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @Override
    @QueryHandler
    public AgencyResponse getAgencyById(FindAgencyByIdQuery query) {
        var agency = agencyRepository.findById(query.getId());
        return agency.map(AgencyResponse::new).orElse(null);
    }

    @Override
    @QueryHandler
    public AgencyResponse getAllAgencies(FindAllAgenciesQuery query) {
        var agencies = new ArrayList<>(agencyRepository.findAll());
        return new AgencyResponse(agencies);
    }

    @Override
    @QueryHandler
    public AgencyResponse getAllActiveAgencies(FindAllActiveAgenciesQuery query) {
        var agencies = new ArrayList<>(agencyRepository.findAgenciesByIsActiveIsTrue());
        return new AgencyResponse(agencies);
    }

    @Override
    @QueryHandler
    public AgencyResponse searchAgencies(SearchAgenciesQuery query) {
        var agencies = new ArrayList<>(agencyRepository.findByFilterRegex(query.getFilter()));
        return new AgencyResponse(agencies);
    }

    @Override
    @QueryHandler
    public PagedAgencyResponse getAgenciesByPage(FindAgenciesByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("agencyName").ascending());
        var totalNumberOfAgencies = agencyRepository.count();
        var agencies = new ArrayList<>(agencyRepository.findAgencyByPage(pageRequest));
        return new PagedAgencyResponse(pageRequest, agencies, totalNumberOfAgencies);
    }

    @Override
    @QueryHandler
    public PagedAgencyResponse getActiveAgenciesByPage(FindActiveAgenciesByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("agencyName").ascending());
        var totalNumberOfAgencies = agencyRepository.countByIsActiveIsTrue();
        var agencies = new ArrayList<>(agencyRepository.findAgenciesByIsActiveIsTrue(pageRequest));
        return new PagedAgencyResponse(pageRequest, agencies, totalNumberOfAgencies);
    }
}
