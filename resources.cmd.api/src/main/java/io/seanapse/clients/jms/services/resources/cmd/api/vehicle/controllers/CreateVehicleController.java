package io.seanapse.clients.jms.services.resources.cmd.api.vehicle.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands.CreateVehicleCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.vehicle.dto.CreateVehicleResponse;
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
@RequestMapping(path = "/api/v1/resources/vehicles")
public class CreateVehicleController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateVehicleController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VEHICLES_MANAGER')")
    public ResponseEntity<CreateVehicleResponse> createVehicle(@Valid @RequestBody CreateVehicleCommand command,
                                                               @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        String id = UUID.randomUUID().toString();

        try {
            command.setId(id);
            command.getVehicle().setCreatedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new CreateVehicleResponse(id, "Successfully created new vehicle"), HttpStatus.CREATED);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to create a new vehicle for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new CreateVehicleResponse(id, errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
