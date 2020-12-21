package io.seanapse.clients.jms.services.resources.core.agency.events;

import io.seanapse.clients.jms.services.resources.core.agency.models.Agency;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgencyUpdatedEvent {
    private String id;

    private Agency agency;
}
