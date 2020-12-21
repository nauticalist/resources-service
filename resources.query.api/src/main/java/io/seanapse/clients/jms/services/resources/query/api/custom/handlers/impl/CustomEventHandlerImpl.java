package io.seanapse.clients.jms.services.resources.query.api.custom.handlers.impl;

import io.seanapse.clients.jms.services.resources.core.custom.events.CustomCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.custom.events.CustomRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.custom.events.CustomUpdatedEvent;
import io.seanapse.clients.jms.services.resources.query.api.custom.handlers.CustomEventHandler;
import io.seanapse.clients.jms.services.resources.query.api.custom.repositories.CustomRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("resources-group")
public class CustomEventHandlerImpl implements CustomEventHandler {
    private final CustomRepository customRepository;

    @Autowired
    public CustomEventHandlerImpl(CustomRepository customRepository) {
        this.customRepository = customRepository;
    }

    @Override
    @EventHandler
    public void on(CustomCreatedEvent event) {
        customRepository.save(event.getCustom());
    }

    @Override
    @EventHandler
    public void on(CustomUpdatedEvent event) {
        customRepository.save(event.getCustom());
    }

    @Override
    @EventHandler
    public void on(CustomRemovedEvent event) {
        customRepository.deleteById(event.getId());
    }
}
