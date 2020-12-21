package io.seanapse.clients.jms.services.resources.cmd.api.custom.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.custom.commands.RemoveCustomCommand;
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
@RequestMapping(path = "/api/v1/resources/customs")
public class RemoveCustomController {
    private final CommandGateway commandGateway;

    @Autowired
    public RemoveCustomController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_CUSTOMS_MANAGER')")
    public ResponseEntity<BaseResponse> removeCustom (@PathVariable(value = "id") String id) {
        try {
            commandGateway.sendAndWait(new RemoveCustomCommand(id));

            return new ResponseEntity<>(new BaseResponse("Custom successfully removed!"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing remove custom request for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
