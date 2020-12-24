package io.seanapse.clients.jms.services.resources.cmd.api.custom.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.custom.commands.CreateCustomCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.custom.dto.CreateCustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/resources/customs")
public class CreateCustomController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateCustomController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_CUSTOMS_MANAGER')")
    public ResponseEntity<CreateCustomResponse> createCustom(@Valid @RequestBody CreateCustomCommand command,
                                                           @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        var id = UUID.randomUUID().toString();

        try {
            command.setId(id);
            command.getCustom().setCreatedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new CreateCustomResponse("Successfully created new custom", id), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to create a new custom for id = " + id;
            log.error(e.toString());
            return new ResponseEntity<>(
                    new CreateCustomResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
