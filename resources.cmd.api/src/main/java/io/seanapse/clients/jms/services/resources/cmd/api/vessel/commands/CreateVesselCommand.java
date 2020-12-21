package io.seanapse.clients.jms.services.resources.cmd.api.vessel.commands;

import io.seanapse.clients.jms.services.resources.core.vessel.models.Vessel;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateVesselCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "No vessel details provided")
    private Vessel vessel;
}
