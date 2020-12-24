package io.seanapse.clients.jms.services.resources.cmd.api.port.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.port.commands.CreatePortCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.port.dto.CreatePortResponse;
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

@RestController
@RequestMapping(path = "/api/v1/resources/ports")
@Slf4j
public class CreatePortController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreatePortController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_PORTS_MANAGER')")
    public ResponseEntity<CreatePortResponse> createPort(@Valid @RequestBody CreatePortCommand command,
                                                         @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        var id = UUID.randomUUID().toString();

        try {
            command.setId(id);
            command.getPort().setCreatedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(
                    new CreatePortResponse("Successfully created new port", id),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to create a new port for id - " + id;
            log.error(e.toString());
            return new ResponseEntity<>(
                    new CreatePortResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
