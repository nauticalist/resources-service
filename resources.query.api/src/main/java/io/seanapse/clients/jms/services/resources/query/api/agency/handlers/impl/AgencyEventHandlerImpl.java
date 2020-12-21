package io.seanapse.clients.jms.services.resources.query.api.agency.handlers.impl;

import io.seanapse.clients.jms.services.resources.core.agency.events.AgencyCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.agency.events.AgencyRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.agency.events.AgencyUpdatedEvent;
import io.seanapse.clients.jms.services.resources.query.api.agency.handlers.AgencyEventHandler;
import io.seanapse.clients.jms.services.resources.query.api.agency.repositories.AgencyRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("resources-group")
public class AgencyEventHandlerImpl implements AgencyEventHandler {
    private final AgencyRepository agencyRepository;

    @Autowired
    public AgencyEventHandlerImpl(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @Override
    @EventHandler
    public void on(AgencyCreatedEvent event) {
        agencyRepository.save(event.getAgency());
    }

    @Override
    @EventHandler
    public void on(AgencyUpdatedEvent event) {
        agencyRepository.save(event.getAgency());
    }

    @Override
    @EventHandler
    public void on(AgencyRemovedEvent event) {
        agencyRepository.deleteById(event.getId());
    }
}
