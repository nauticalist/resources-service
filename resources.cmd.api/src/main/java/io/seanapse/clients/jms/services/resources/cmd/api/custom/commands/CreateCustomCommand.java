package io.seanapse.clients.jms.services.resources.cmd.api.custom.commands;

import io.seanapse.clients.jms.services.resources.core.custom.models.Custom;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateCustomCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "no custom details provided")
    private Custom custom;
}
