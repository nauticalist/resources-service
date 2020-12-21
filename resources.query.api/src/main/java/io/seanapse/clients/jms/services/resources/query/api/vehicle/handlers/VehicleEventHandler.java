package io.seanapse.clients.jms.services.resources.query.api.vehicle.handlers;

import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleDriverSetEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleUpdatedEvent;

public interface VehicleEventHandler {
    void on(VehicleCreatedEvent event);
    void on(VehicleUpdatedEvent event);
    void on(VehicleRemovedEvent event);
    void on(VehicleDriverSetEvent event);
}
