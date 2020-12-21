package io.seanapse.clients.jms.services.resources.cmd.api.vessel.controllers;

import io.seanapse.clients.jms.services.resources.cmd.api.vessel.commands.UpdateVesselCommand;
import io.seanapse.clients.jms.services.resources.cmd.api.vessel.dto.CreateVesselResponse;
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
@RequestMapping(path = "/api/v1/resources/vessels")
public class UpdateVesselController {
    private final CommandGateway commandGateway;

    @Autowired
    public UpdateVesselController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VESSELS_MANAGER')")
    public ResponseEntity<BaseResponse> updateVessel(@PathVariable(value = "{id}") String id,
                                                     @Valid @RequestBody UpdateVesselCommand command,
                                                     @AuthenticationPrincipal Jwt principal) {
        String userId = (String) principal.getClaims().get("sub");

        try {
            command.setId(id);
            command.getVessel().setModifiedBy(userId);

            commandGateway.sendAndWait(command);

            return new ResponseEntity<>(new BaseResponse("Vessel successfully updated!"), HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to update vessel for id - " + id;
            log.error(e.toString());

            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
