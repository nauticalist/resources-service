package io.seanapse.clients.jms.services.resources.cmd.api.agency.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.agency.commands.RemoveAgencyCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.agency.commands.UpdateAgencyCommand;
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

@RestController
@RequestMapping(path = "/api/v1/resources/agencies")
@Slf4j
public class RemoveAgencyController {
    private final CommandGateway commandGateway;

    @Autowired
    public RemoveAgencyController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_AGENCIES_MANAGER')")
    public ResponseEntity<BaseResponse> removeAgency(@PathVariable(value = "id") String id) {
        try {

            commandGateway.sendAndWait(new RemoveAgencyCommand(id));

            return new ResponseEntity<>(new BaseResponse("Successfully removed agency"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to remove agency for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
