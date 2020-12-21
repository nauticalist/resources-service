package io.seanapse.clients.jms.services.resources.query.api.vehicle.handlers.impl;

import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleDriverSetEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleUpdatedEvent;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.handlers.VehicleEventHandler;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.repositories.VehicleRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("resources-group")
public class VehicleEventHandlerImpl implements VehicleEventHandler {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleEventHandlerImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @EventHandler
    public void on(VehicleCreatedEvent event) {
        vehicleRepository.save(event.getVehicle());

    }

    @Override
    @EventHandler
    public void on(VehicleUpdatedEvent event) {
        vehicleRepository.save(event.getVehicle());
    }

    @Override
    @EventHandler
    public void on(VehicleRemovedEvent event) {
        vehicleRepository.deleteById(event.getId());
    }

    @Override
    @EventHandler
    public void on(VehicleDriverSetEvent event) {
        vehicleRepository.save(event.getVehicle());
    }
}
