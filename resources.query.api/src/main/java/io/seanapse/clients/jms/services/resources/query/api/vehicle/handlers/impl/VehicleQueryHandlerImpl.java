package io.seanapse.clients.jms.services.resources.query.api.vehicle.handlers.impl;

import io.seanapse.clients.jms.services.resources.query.api.vehicle.dto.PagedVehicleRespone;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.dto.VehicleResponse;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.handlers.VehicleQueryHandler;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.queries.*;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.repositories.VehicleRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VehicleQueryHandlerImpl implements VehicleQueryHandler {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleQueryHandlerImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @QueryHandler
    public VehicleResponse getVehicleById(FindVehicleByIdQuery query) {
        var vehicle = vehicleRepository.findById(query.getId());
        return vehicle.map(VehicleResponse::new).orElse(null);
    }

    @Override
    @QueryHandler
    public VehicleResponse getAllVehicles(FindAllVehiclesQuery query) {
        var vehicles = new ArrayList<>(vehicleRepository.findAll());
        return new VehicleResponse(vehicles);
    }

    @Override
    @QueryHandler
    public VehicleResponse getActiveVehicles(FindActiveVehiclesQuery query) {
        var vehicles = new ArrayList<>(vehicleRepository.findVehiclesByIsActiveIsTrue());
        return new VehicleResponse(vehicles);
    }

    @Override
    @QueryHandler
    public VehicleResponse searchVehicles(SearchVehiclesQuery query) {
        var vessels = new ArrayList<>(vehicleRepository.findByFilterRegex(query.getFilter()));
        return new VehicleResponse(vessels);
    }

    @Override
    @QueryHandler
    public PagedVehicleRespone getVehiclesByPage(FindVehiclesByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("identificationPlate").ascending());
        var totalNumberOfVehicles = vehicleRepository.count();
        var vehicles = new ArrayList<>(vehicleRepository.findVehiclesByPage(pageRequest));
        return new PagedVehicleRespone(pageRequest, vehicles, totalNumberOfVehicles);
    }

    @Override
    @QueryHandler
    public PagedVehicleRespone getActiveVehiclesByPage(FindActiveVehiclesByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("identificationPlate").ascending());
        var totalNumberOfVehicles = vehicleRepository.countByIsActiveIsTrue();
        var vehicles = new ArrayList<>(vehicleRepository.findVehiclesByIsActiveIsTrue(pageRequest));
        return new PagedVehicleRespone(pageRequest, vehicles, totalNumberOfVehicles);
    }
}
