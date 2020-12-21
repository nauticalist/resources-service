package io.seanapse.clients.jms.services.resources.cmd.api.custom.commands;

import io.seanapse.clients.jms.services.resources.core.custom.models.Custom;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UpdateCustomCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "no custom details provided")
    private Custom custom;
}
