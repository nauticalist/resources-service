package io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.seanapse.clients.jms.services.resources.core.vehicles.model.Vehicle;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SetVehicleDriverCommand {
    @TargetAggregateIdentifier
    private String id;

    @NotNull(message = "You must select a user")
    private String driver;

    @JsonIgnore
    private String userId;
}
