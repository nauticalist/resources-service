package io.seanapse.clients.jms.services.resources.cmd.api.agency.aggregates;

import io.seanapse.clients.jms.services.resources.cmd.api.agency.commands.CreateAgencyCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.agency.commands.RemoveAgencyCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.agency.commands.UpdateAgencyCommand;
import io.seanapse.clients.jms.services.resources.core.agency.events.AgencyCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.agency.events.AgencyRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.agency.events.AgencyUpdatedEvent;
import io.seanapse.clients.jms.services.resources.core.agency.models.Agency;
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
public class AgencyAggregate {
    @AggregateIdentifier
    private String id;

    private Agency agency;

    @CommandHandler
    public AgencyAggregate(CreateAgencyCommand command) {
        var newAgency = command.getAgency();
        newAgency.setId(command.getId());
        newAgency.setCreatedAt(new Date());

        var event = AgencyCreatedEvent.builder()
                .id(command.getId())
                .agency(newAgency)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AgencyCreatedEvent event) {
        this.id = event.getId();
        this.agency = event.getAgency();
    }

    @CommandHandler
    public void handle(UpdateAgencyCommand command) {
        var updatedAgency = command.getAgency();
        updatedAgency.setId(command.getId());
        updatedAgency.setCreatedAt(this.agency.getCreatedAt());
        updatedAgency.setCreatedBy(this.agency.getCreatedBy());
        updatedAgency.setModifiedAt(new Date());

        var event = AgencyUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .agency(updatedAgency)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AgencyUpdatedEvent event) {
        this.agency = event.getAgency();
    }

    @CommandHandler
    public void handle(RemoveAgencyCommand command) {
        var event = new AgencyRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AgencyRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }

}
