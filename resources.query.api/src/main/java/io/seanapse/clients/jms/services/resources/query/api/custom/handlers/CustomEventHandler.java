package io.seanapse.clients.jms.services.resources.query.api.custom.handlers;

import io.seanapse.clients.jms.services.resources.core.custom.events.CustomCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.custom.events.CustomRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.custom.events.CustomUpdatedEvent;

public interface CustomEventHandler {
    void on(CustomCreatedEvent event);
    void on(CustomUpdatedEvent event);
    void on(CustomRemovedEvent event);
}
