package io.seanapse.clients.jms.services.resources.core.vessel.events;

import io.seanapse.clients.jms.services.resources.core.vessel.models.Vessel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VesselUpdatedEvent {
    private String id;

    private Vessel vessel;
}
