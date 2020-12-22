package io.seanapse.clients.jms.services.resources.query.api.vessel.controllers;

import io.seanapse.clients.jms.services.resources.query.api.vessel.dto.PagedVesselResponse;
import io.seanapse.clients.jms.services.resources.query.api.vessel.dto.VesselResponse;
import io.seanapse.clients.jms.services.resources.query.api.vessel.queries.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/resources/vessels")
public class VesselController {
    private final QueryGateway queryGateway;

    @Autowired
    public VesselController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VESSELS_USER')")
    public ResponseEntity<VesselResponse> getAllVessels() {
        try {
            var query = new FindAllVesselsQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(VesselResponse.class)).join();

            if (response == null || response.getVessels() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get all vessels request";
            log.error(e.toString());
            return new ResponseEntity<>(new VesselResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/active")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VESSELS_USER')")
    public ResponseEntity<VesselResponse> getActiveVessels() {
        try {
            var query = new FindActiveVesselsQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(VesselResponse.class)).join();

            if (response == null || response.getVessels() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get active vessels request";
            log.error(e.toString());
            return new ResponseEntity<>(new VesselResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VESSELS_USER')")
    public ResponseEntity<VesselResponse> getVesselById(@PathVariable(value = "id") String id) {
        try {
            var query = new FindVesselByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(VesselResponse.class)).join();

            if (response == null || response.getVessels() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get vessel with id request - " + id;
            log.error(e.toString());
            return new ResponseEntity<>(new VesselResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter/{filter}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VESSELS_USER')")
    public ResponseEntity<VesselResponse> searchVessel(@PathVariable(value = "filter") String filter) {
        try {
            var query = new SearchVesselsQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(VesselResponse.class)).join();

            if (response == null || response.getVessels() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete search request";
            log.error(e.toString());
            return new ResponseEntity<>(new VesselResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VESSELS_USER')")
    public ResponseEntity<PagedVesselResponse> getVesselsByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindVesselsByPageQuery(page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PagedVesselResponse.class)).join();

            if (response == null || response.getVessels() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get vessels by page request";
            log.error(e.toString());
            return new ResponseEntity<>(new PagedVesselResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/active/")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VESSELS_USER')")
    public ResponseEntity<PagedVesselResponse> getActiveVesselsByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                      @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindActiveVesselsByPageQuery(page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PagedVesselResponse.class)).join();

            if (response == null || response.getVessels() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get vessels by page request";
            log.error(e.toString());
            return new ResponseEntity<>(new PagedVesselResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
