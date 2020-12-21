package io.seanapse.clients.jms.services.resources.core.vehicles.events;

import io.seanapse.clients.jms.services.resources.core.vehicles.model.Vehicle;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleUpdatedEvent {
    private String id;

    private Vehicle vehicle;
}
