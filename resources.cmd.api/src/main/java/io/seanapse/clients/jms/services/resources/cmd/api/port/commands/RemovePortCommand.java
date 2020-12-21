package io.seanapse.clients.jms.services.resources.cmd.api.port.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RemovePortCommand {
    @TargetAggregateIdentifier
    private String id;
}
