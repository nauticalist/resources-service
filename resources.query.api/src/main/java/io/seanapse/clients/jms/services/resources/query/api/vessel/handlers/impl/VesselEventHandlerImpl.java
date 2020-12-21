package io.seanapse.clients.jms.services.resources.query.api.vessel.handlers.impl;

import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselUpdatedEvent;
import io.seanapse.clients.jms.services.resources.query.api.vessel.handlers.VesselEventHandler;
import io.seanapse.clients.jms.services.resources.query.api.vessel.repositories.VesselRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("resources-group")
public class VesselEventHandlerImpl implements VesselEventHandler {
    private final VesselRepository vesselRepository;

    @Autowired
    public VesselEventHandlerImpl(VesselRepository vesselRepository) {
        this.vesselRepository = vesselRepository;
    }

    @Override
    @EventHandler
    public void on(VesselCreatedEvent event) {
        vesselRepository.save(event.getVessel());
    }

    @Override
    @EventHandler
    public void on(VesselUpdatedEvent event) {
        vesselRepository.save(event.getVessel());
    }

    @Override
    @EventHandler
    public void on(VesselRemovedEvent event) {
        vesselRepository.deleteById(event.getId());
    }
}
