package io.seanapse.clients.jms.services.resources.cmd.api.agency.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.agency.commands.CreateAgencyCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.agency.commands.UpdateAgencyCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.agency.dto.CreateAgencyResponse;
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
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/resources/agencies")
@Slf4j
public class UpdateAgencyController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdateAgencyController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_AGENCIES_MANAGER')")
    public ResponseEntity<BaseResponse> updateAgency(@PathVariable(value = "id") String id,
                                                             @Valid @RequestBody UpdateAgencyCommand command,
                                                             @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        try {
            command.setId(id);
            command.getAgency().setModifiedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new BaseResponse("Successfully updated agency"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to update agency for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
