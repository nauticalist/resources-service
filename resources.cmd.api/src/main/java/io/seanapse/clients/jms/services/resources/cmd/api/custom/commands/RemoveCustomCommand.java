package io.seanapse.clients.jms.services.resources.cmd.api.custom.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RemoveCustomCommand {
    @TargetAggregateIdentifier
    private String id;
}
