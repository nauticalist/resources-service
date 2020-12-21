package io.seanapse.clients.jms.services.resources.cmd.api.vehicle.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands.SetVehicleDriverCommand;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/resources/vehicles")
public class SetVehicleDriverController {
    private final CommandGateway commandGateway;

    @Autowired
    public SetVehicleDriverController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/{id}/setDriver")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VEHICLES_MANAGER')")
    public ResponseEntity<BaseResponse> setVehicleDriver(@PathVariable(value = "id") String id,
                                                         @Valid @RequestBody SetVehicleDriverCommand command,
                                                         @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        try {
            command.setId(id);
            command.setUserId(userId);
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new BaseResponse("Vehicle driver set!"), HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Error while processign request to set driver for vehile with id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
