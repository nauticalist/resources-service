package io.seanapse.clients.jms.services.resources.core.custom.events;

import io.seanapse.clients.jms.services.resources.core.custom.models.Custom;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomCreatedEvent {
    private String id;

    private Custom custom;
}
