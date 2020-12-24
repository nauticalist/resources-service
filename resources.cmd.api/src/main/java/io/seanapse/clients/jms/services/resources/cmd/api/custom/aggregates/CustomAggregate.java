package io.seanapse.clients.jms.services.resources.cmd.api.custom.aggregates;

import io.seanapse.clients.jms.services.resources.cmd.api.custom.commands.CreateCustomCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.custom.commands.RemoveCustomCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.custom.commands.UpdateCustomCommand;
import io.seanapse.clients.jms.services.resources.core.custom.events.CustomCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.custom.events.CustomRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.custom.events.CustomUpdatedEvent;
import io.seanapse.clients.jms.services.resources.core.custom.models.Custom;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;
import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class CustomAggregate {
    @AggregateIdentifier
    private String id;

    private Custom custom;

    @CommandHandler
    public CustomAggregate(CreateCustomCommand command) {
        var newCustom = command.getCustom();
        newCustom.setId(command.getId());
        newCustom.setCreatedAt(new Date());
        var event = CustomCreatedEvent.builder()
                .id(command.getId())
                .custom(newCustom)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CustomCreatedEvent event) {
        this.id = event.getId();
        this.custom = event.getCustom();
    }

    @CommandHandler
    public void handle(UpdateCustomCommand command) {
        var updatedCustom = command.getCustom();
        updatedCustom.setId(command.getId());
        updatedCustom.setModifiedAt(new Date());
        updatedCustom.setCreatedAt(this.custom.getCreatedAt());
        updatedCustom.setCreatedBy(this.custom.getCreatedBy());

        var event = CustomUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .custom(updatedCustom)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CustomUpdatedEvent event) {
        this.custom = event.getCustom();
    }

    @CommandHandler
    public void handle(RemoveCustomCommand command) {
        var event = new CustomRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CustomRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
