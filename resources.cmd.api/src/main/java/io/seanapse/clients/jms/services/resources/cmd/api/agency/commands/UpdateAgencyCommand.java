package io.seanapse.clients.jms.services.resources.cmd.api.agency.commands;

import io.seanapse.clients.jms.services.resources.core.agency.models.Agency;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UpdateAgencyCommand {
    @TargetAggregateIdentifier
    private String id;

    @Valid
    @NotNull(message = "no agency details provided")
    private Agency agency;
}
