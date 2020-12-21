package io.seanapse.clients.jms.services.resources.cmd.api.vessel.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.vessel.commands.CreateVesselCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.vessel.dto.CreateVesselResponse;
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
@RequestMapping(path = "/api/v1/resources/vessels")
public class CreateVesselController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateVesselController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VESSELS_MANAGER')")
    public ResponseEntity<CreateVesselResponse> createVessel(@Valid @RequestBody CreateVesselCommand command,
                                                             @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        var id = UUID.randomUUID().toString();
        try {
            command.setId(id);
            command.getVessel().setCreatedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new CreateVesselResponse(id, "Successfully created new vessel"), HttpStatus.CREATED);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to create a new vessel for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new CreateVesselResponse(id, errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
