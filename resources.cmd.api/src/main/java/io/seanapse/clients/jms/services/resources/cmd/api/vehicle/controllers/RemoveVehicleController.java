package io.seanapse.clients.jms.services.resources.cmd.api.vehicle.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.vehicle.commands.RemoveVehicleCommand;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/resources/vehicles")
public class RemoveVehicleController {
    private final CommandGateway commandGateway;

    @Autowired
    public RemoveVehicleController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VEHICLES_MANAGER')")
    public ResponseEntity<BaseResponse> removeVehicle(@PathVariable(value = "id") String id) {
        try {
            commandGateway.sendAndWait(new RemoveVehicleCommand(id));

            return new ResponseEntity<>(new BaseResponse("Vehicle successfully removed!"), HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to remove vehicle for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
