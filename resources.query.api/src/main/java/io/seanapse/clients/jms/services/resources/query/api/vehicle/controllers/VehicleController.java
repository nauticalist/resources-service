package io.seanapse.clients.jms.services.resources.query.api.vehicle.controllers;

import io.seanapse.clients.jms.services.resources.query.api.vehicle.dto.PagedVehicleRespone;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.dto.VehicleResponse;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.queries.*;
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
@RequestMapping(path = "/api/v1/resources/vehicles")
public class VehicleController {
    private final QueryGateway queryGateway;

    @Autowired
    public VehicleController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VEHICLES_USER')")
    public ResponseEntity<VehicleResponse> getAllVehicles() {
        try {
            var query = new FindAllVehiclesQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(VehicleResponse.class)).join();

            if (response == null || response.getVehicles() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get all vehicles request";
            log.error(e.toString());
            return new ResponseEntity<>(new VehicleResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VEHICLES_USER')")
    public ResponseEntity<VehicleResponse> getActiveVehicles() {
        try {
            var query = new FindActiveVehiclesQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(VehicleResponse.class)).join();

            if (response == null || response.getVehicles() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get active vehicles request";
            log.error(e.toString());
            return new ResponseEntity<>(new VehicleResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VEHICLES_USER')")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable(value = "id") String id) {
        try {
            var query = new FindVehicleByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(VehicleResponse.class)).join();

            if (response == null || response.getVehicles() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get vehicle with id request - " + id;
            log.error(e.toString());
            return new ResponseEntity<>(new VehicleResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter/{filter}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VEHICLES_USER')")
    public ResponseEntity<VehicleResponse> searchVehicles(@PathVariable(value = "filter") String filter) {
        try {
            var query = new SearchVehiclesQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(VehicleResponse.class)).join();

            if (response == null || response.getVehicles() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete search request";
            log.error(e.toString());
            return new ResponseEntity<>(new VehicleResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VEHICLES_USER')")
    public ResponseEntity<PagedVehicleRespone> getVehiclesByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindVehiclesByPageQuery(page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PagedVehicleRespone.class)).join();

            if (response == null || response.getVehicles() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get vehicles by page request";
            log.error(e.toString());
            return new ResponseEntity<>(new PagedVehicleRespone(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active/")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_VEHICLES_USER')")
    public ResponseEntity<PagedVehicleRespone> getActiveVehiclesByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                       @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindActiveVehiclesByPageQuery(page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PagedVehicleRespone.class)).join();

            if (response == null || response.getVehicles() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Failed to complete get active vehicles by page request";
            log.error(e.toString());
            return new ResponseEntity<>(new PagedVehicleRespone(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
