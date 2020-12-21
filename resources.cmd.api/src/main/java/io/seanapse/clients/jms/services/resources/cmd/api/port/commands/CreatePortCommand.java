package io.seanapse.clients.jms.services.resources.cmd.api.port.commands;

import io.seanapse.clients.jms.services.resources.core.port.models.Port;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreatePortCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "no port details provided")
    private Port port;
}
