package io.seanapse.clients.jms.services.resources.cmd.api.port.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.port.commands.UpdatePortCommand;
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
@RequestMapping(path = "/api/v1/resources/ports")
@Slf4j
public class UpdatePortController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdatePortController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_PORTS_MANAGER')")
    public ResponseEntity<BaseResponse> updatePort(@PathVariable(value = "id") String id,
                                                   @Valid @RequestBody UpdatePortCommand command,
                                                   @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");
        try {
            command.setId(id);
            command.getPort().setModifiedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(
                    new BaseResponse("Port successfully updated!"),
                    HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to update port for id - " + id;
            log.error(e.toString());
            return new ResponseEntity<>(
                    new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
