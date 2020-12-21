package io.seanapse.clients.jms.services.resources.cmd.api.agency.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class RemoveAgencyCommand {
    @TargetAggregateIdentifier
    private String id;
}
