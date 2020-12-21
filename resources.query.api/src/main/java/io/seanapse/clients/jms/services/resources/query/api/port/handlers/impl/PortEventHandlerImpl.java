package io.seanapse.clients.jms.services.resources.query.api.port.handlers.impl;

import io.seanapse.clients.jms.services.resources.core.port.events.PortCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.port.events.PortUpdatedEvent;
import io.seanapse.clients.jms.services.resources.core.port.events.PortRemovedEvent;
import io.seanapse.clients.jms.services.resources.query.api.port.handlers.PortEventHandler;
import io.seanapse.clients.jms.services.resources.query.api.port.repositories.PortRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("resources-group")
public class PortEventHandlerImpl implements PortEventHandler {
    private final PortRepository portRepository;

    @Autowired
    public PortEventHandlerImpl(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    @Override
    @EventHandler
    public void on(PortCreatedEvent event) {
        portRepository.save(event.getPort());
    }

    @Override
    @EventHandler
    public void on(PortUpdatedEvent event) {
        portRepository.save(event.getPort());
    }

    @Override
    @EventHandler
    public void on(PortRemovedEvent event) {
        portRepository.deleteById(event.getId());
    }


}
