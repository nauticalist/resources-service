package io.seanapse.clients.jms.services.resources.cmd.api.port.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.port.commands.RemovePortCommand;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/resources/ports")
@Slf4j
public class RemovePortController {
    private final CommandGateway commandGateway;

    @Autowired
    public RemovePortController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_PORTS_MANAGER')")
    public ResponseEntity<BaseResponse> removePort(@PathVariable(value = "id") String id) {
        try {
            commandGateway.sendAndWait(new RemovePortCommand(id));

            return new ResponseEntity<>(new BaseResponse("Port successfully removed!"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing remove port request for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
