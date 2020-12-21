package io.seanapse.clients.jms.services.resources.query.api.port.controllers;

import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import io.seanapse.clients.jms.services.resources.query.api.port.dto.PaginatedPortResponse;
import io.seanapse.clients.jms.services.resources.query.api.port.dto.PortResponse;
import io.seanapse.clients.jms.services.resources.query.api.port.queries.FindAllPortsQuery;
import io.seanapse.clients.jms.services.resources.query.api.port.queries.FindPortByIdQuery;
import io.seanapse.clients.jms.services.resources.query.api.port.queries.FindPortsByPageQuery;
import io.seanapse.clients.jms.services.resources.query.api.port.queries.SearchPortsQuery;
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
@RequestMapping(path = "/api/v1/resources/ports")
public class PortController {
    private final QueryGateway queryGateway;

    @Autowired
    public PortController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_PORTS_USER')")
    public ResponseEntity<PortResponse> getAllPorts() {
        try {
            var query = new FindAllPortsQuery();
            var response = queryGateway.query(query,
                    ResponseTypes.instanceOf(PortResponse.class)).join();

            if(response == null || response.getPorts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all ports request";
            log.error(e.toString());

            return new ResponseEntity<>(new PortResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_PORTS_USER')")
    public ResponseEntity<PortResponse> getPortById(@PathVariable(value = "id") String id) {
        try {
            var query = new FindPortByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PortResponse.class)).join();
            if (response == null || response.getPorts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get port by id request";
            log.error(e.toString());

            return new ResponseEntity<>(new PortResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/filter/{filter}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_PORTS_USER')")
    public ResponseEntity<PortResponse> searchPortByFilter(@PathVariable(value = "filter") String filter) {
        try {
            var query = new SearchPortsQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PortResponse.class)).join();
            if (response == null || response.getPorts() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete search request";
            log.error(e.toString());

            return new ResponseEntity<>(new PortResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_PORTS_USER')")
    public ResponseEntity<PaginatedPortResponse> getPortsByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindPortsByPageQuery(page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PaginatedPortResponse.class)).join();
            if (response == null || response.getContent() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get port by page request";
            log.error(e.toString());

            return new ResponseEntity<>(new PaginatedPortResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
