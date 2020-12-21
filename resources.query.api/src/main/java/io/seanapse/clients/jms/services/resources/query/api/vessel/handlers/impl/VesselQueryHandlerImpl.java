package io.seanapse.clients.jms.services.resources.query.api.vessel.handlers.impl;

import io.seanapse.clients.jms.services.resources.query.api.vessel.dto.PagedVesselResponse;
import io.seanapse.clients.jms.services.resources.query.api.vessel.dto.VesselResponse;
import io.seanapse.clients.jms.services.resources.query.api.vessel.handlers.VesselQueryHandler;
import io.seanapse.clients.jms.services.resources.query.api.vessel.queries.*;
import io.seanapse.clients.jms.services.resources.query.api.vessel.repositories.VesselRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VesselQueryHandlerImpl implements VesselQueryHandler {
    private final VesselRepository vesselRepository;

    @Autowired
    public VesselQueryHandlerImpl(VesselRepository vesselRepository) {
        this.vesselRepository = vesselRepository;
    }

    @Override
    @QueryHandler
    public VesselResponse getVesselById(FindVesselByIdQuery query) {
        var vessel = vesselRepository.findById(query.getId());
        return vessel.map(VesselResponse::new).orElse(null);
    }

    @Override
    @QueryHandler
    public VesselResponse getAllVessels(FindAllVesselsQuery query) {
        var vessels = new ArrayList<>(vesselRepository.findAll());
        return new VesselResponse(vessels);
    }

    @Override
    @QueryHandler
    public VesselResponse getActiveVessels(FindActiveVesselsQuery query) {
        var vessels = new ArrayList<>(vesselRepository.findVesselsByActiveIsTrue());
        return new VesselResponse(vessels);
    }

    @Override
    @QueryHandler
    public VesselResponse searchVessels(SearchVesselsQuery query) {
        var vessels = new ArrayList<>(vesselRepository.findByFilterRegex(query.getFilter()));
        return new VesselResponse(vessels);
    }

    @Override
    @QueryHandler
    public PagedVesselResponse getVesselsByPage(FindVesselsByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("vesselName").ascending());
        var totalNumberOfVessels = vesselRepository.count();
        var vessels = new ArrayList<>(vesselRepository.findVessels(pageRequest));
        return new PagedVesselResponse(pageRequest, vessels, totalNumberOfVessels);
    }

    @Override
    @QueryHandler
    public PagedVesselResponse getActiveVesselsByPage(FindActiveVesselsByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("vesselName").ascending());
        var totalNumberOfVessels = vesselRepository.countByActiveIsTrue();
        var vessels = new ArrayList<>(vesselRepository.findVesselsByActiveIsTrue(pageRequest));
        return new PagedVesselResponse(pageRequest, vessels, totalNumberOfVessels);
    }
}
