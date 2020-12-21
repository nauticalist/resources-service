package io.seanapse.clients.jms.services.resources.query.api.vessel.handlers;

import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselUpdatedEvent;

public interface VesselEventHandler {
    void on(VesselCreatedEvent event);
    void on(VesselUpdatedEvent event);
    void on(VesselRemovedEvent event);
}
