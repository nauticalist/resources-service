package io.seanapse.clients.jms.services.resources.cmd.api.vehicle.aggregate;

import io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands.CreateVehicleCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands.RemoveVehicleCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands.SetVehicleDriverCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands.UpdateVehicleCommand;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleCreatedEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleDriverSetEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleRemovedEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.events.VehicleUpdatedEvent;
import io.seanapse.clients.jms.services.resources.core.vehicles.model.Vehicle;
import io.seanapse.clients.jms.services.resources.core.vessel.events.VesselRemovedEvent;
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
public class VehicleAggregate {
    @AggregateIdentifier
    private String id;

    private Vehicle vehicle;

    @CommandHandler
    public VehicleAggregate(CreateVehicleCommand command) {
        var newVehicle = command.getVehicle();
        newVehicle.setId(command.getId());
        newVehicle.setCreatedAt(new Date());

        var event = VehicleCreatedEvent.builder()
                .id(command.getId())
                .vehicle(newVehicle)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VehicleCreatedEvent event) {
        this.id = event.getId();
        this.vehicle = event.getVehicle();
    }

    @CommandHandler
    public void handle(UpdateVehicleCommand command) {
        var updatedVessel = command.getVehicle();
        updatedVessel.setId(command.getId());
        updatedVessel.setModifiedAt(new Date());

        var event = VehicleUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .vehicle(updatedVessel)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VehicleUpdatedEvent event){
        this.vehicle = event.getVehicle();
    }

    @CommandHandler
    public void handle(RemoveVehicleCommand command) {
        var event = new VesselRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VehicleRemovedEvent event){
        AggregateLifecycle.markDeleted();
    }

    @CommandHandler
    public void handle(SetVehicleDriverCommand command) {
        var driver = command.getDriver();
        var userId = command.getUserId();

        this.vehicle.setDriver(driver);
        this.vehicle.setModifiedAt(new Date());
        this.vehicle.setModifiedBy(userId);

        var event = VehicleDriverSetEvent.builder()
                .id(command.getId())
                .vehicle(this.vehicle)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(VehicleDriverSetEvent event) {
        this.vehicle = event.getVehicle();
    }

}
