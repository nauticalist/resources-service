package io.seanapse.clients.jms.services.resources.core.port.events;

import io.seanapse.clients.jms.services.resources.core.port.models.Port;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortUpdatedEvent {
    private String id;

    private Port port;
}
