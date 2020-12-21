package io.seanapse.clients.jms.services.resources.cmd.api.port.aggregates;

import io.seanapse.clients.jms.services.resources.cmd.api.port.commands.CreatePortCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.port.commands.RemovePortCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.port.commands.UpdatePortCommand;
import io.seanapse.clients.jms.services.resources.core.port.events.PortCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.port.events.PortRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.port.events.PortUpdatedEvent;
import io.seanapse.clients.jms.services.resources.core.port.models.Port;
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
public class PortAggregate {
    @AggregateIdentifier
    private String id;

    private Port port;

    @CommandHandler
    public PortAggregate(CreatePortCommand command) {
        var newPort = command.getPort();
        newPort.setId(command.getId());
        newPort.setCreatedAt(new Date());
        var event = PortCreatedEvent.builder()
                .id(command.getId())
                .port(newPort)
                .build();

        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void on(PortCreatedEvent event) {
        this.id = event.getId();
        this.port = event.getPort();
    }

    @CommandHandler
    public void handle(UpdatePortCommand command) {
        var updatedPort = command.getPort();
        updatedPort.setId(command.getId());
        updatedPort.setModifiedAt(new Date());

        var event = PortUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .port(updatedPort)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PortUpdatedEvent event) {
        this.port = event.getPort();
    }

    @CommandHandler
    public void handle(RemovePortCommand command) {
        var event = new PortRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PortRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
