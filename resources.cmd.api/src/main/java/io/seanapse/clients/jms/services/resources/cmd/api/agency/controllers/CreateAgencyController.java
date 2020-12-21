package io.seanapse.clients.jms.services.resources.cmd.api.agency.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.agency.commands.CreateAgencyCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.agency.dto.CreateAgencyResponse;
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
@RequestMapping(path = "/api/v1/resources/agencies")
@Slf4j
public class CreateAgencyController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateAgencyController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_AGENCIES_MANAGER')")
    public ResponseEntity<CreateAgencyResponse> createAgency(@Valid @RequestBody CreateAgencyCommand command,
                                                             @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        var id = UUID.randomUUID().toString();

        try {
            command.setId(id);
            command.getAgency().setCreatedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new CreateAgencyResponse(id, "Successfully created new agency"), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to create a new agency for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new CreateAgencyResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
