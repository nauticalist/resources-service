package io.seanapse.clients.jms.services.resources.cmd.api.vessel.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RemoveVesselCommand {
    @TargetAggregateIdentifier
    private String id;
}
