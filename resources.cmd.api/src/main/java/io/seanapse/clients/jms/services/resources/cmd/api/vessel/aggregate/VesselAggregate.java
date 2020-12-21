package io.seanapse.clients.jms.services.resources.cmd.api.vessel.aggregate;

import io.seanapse.clients.jms.services.resources.cmd.api.vessel.commands.CreateVesselCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.vessel.commands.RemoveVesselCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.vessel.commands.UpdateVesselCommand;
import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselUpdatedEvent;
import io.seanapse.clients.jms.services.resources.core.vessel.models.Vessel;
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
public class VesselAggregate {
    @AggregateIdentifier
    private String id;

    private Vessel vessel;

    @CommandHandler
    public VesselAggregate(CreateVesselCommand command) {
        var newVessel = command.getVessel();
        newVessel.setId(command.getId());
        newVessel.setCreatedAt(new Date());

        var event = VesselCreatedEvent.builder()
                .id(command.getId())
                .vessel(newVessel)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VesselCreatedEvent event) {
        this.id = event.getId();
        this.vessel = event.getVessel();
    }

    @CommandHandler
    public void handle(UpdateVesselCommand command) {
        var updatedVessel = command.getVessel();
        updatedVessel.setId(command.getId());
        updatedVessel.setModifiedAt(new Date());

        var event = VesselUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .vessel(updatedVessel)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VesselUpdatedEvent event) {
        this.vessel = event.getVessel();
    }

    @CommandHandler
    public void handle(RemoveVesselCommand command) {
        var event = new VesselRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VesselRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
