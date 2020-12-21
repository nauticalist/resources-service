package io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RemoveVehicleCommand {
    @TargetAggregateIdentifier
    private String id;
}
