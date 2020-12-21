package io.seanapse.clients.jms.services.resources.query.api.port.handlers;

import io.seanapse.clients.jms.services.resources.core.port.events.PortCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.port.events.PortUpdatedEvent;
import io.seanapse.clients.jms.services.resources.core.port.events.PortRemovedEvent;

public interface PortEventHandler {
    void on(PortCreatedEvent event);
    void on(PortUpdatedEvent event);
    void on(PortRemovedEvent event);
}
