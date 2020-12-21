package io.seanapse.clients.jms.services.resources.cmd.api.custom.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.custom.commands.UpdateCustomCommand;
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
@RequestMapping(path = "/api/v1/resources/customs")
public class UpdateCustomController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdateCustomController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_CUSTOMS_MANAGER')")
    public ResponseEntity<BaseResponse> updateCustom(@PathVariable(value = "id") String id,
                                                   @Valid @RequestBody UpdateCustomCommand command,
                                                   @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");

        try {
            command.setId(id);
            command.getCustom().setModifiedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new BaseResponse("Custom successfully updated!"), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to update custom for id - " + id;
            log.error(e.toString());
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
