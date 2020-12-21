package io.seanapse.clients.jms.services.resources.query.api.agency.handlers;

import io.seanapse.clients.jms.services.resources.core.agency.events.AgencyCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.agency.events.AgencyRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.agency.events.AgencyUpdatedEvent;

public interface AgencyEventHandler {
    void on(AgencyCreatedEvent event);
    void on(AgencyUpdatedEvent event);
    void on(AgencyRemovedEvent event);
}
