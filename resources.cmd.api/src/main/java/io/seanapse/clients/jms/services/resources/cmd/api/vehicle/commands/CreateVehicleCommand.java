package io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands;

import io.seanapse.clients.jms.services.resources.core.vehicles.model.Vehicle;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateVehicleCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "No vehicle details provided")
    private Vehicle vehicle;
}
