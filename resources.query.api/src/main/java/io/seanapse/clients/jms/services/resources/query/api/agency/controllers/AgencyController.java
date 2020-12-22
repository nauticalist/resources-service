package io.seanapse.clients.jms.services.resources.query.api.agency.controllers;

import io.seanapse.clients.jms.services.resources.query.api.agency.dto.AgencyResponse;
import io.seanapse.clients.jms.services.resources.query.api.agency.dto.PagedAgencyResponse;
import io.seanapse.clients.jms.services.resources.query.api.agency.queries.*;
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
@RequestMapping(path = "/api/v1/resources/agencies")
public class AgencyController {
    private final QueryGateway queryGateway;

    @Autowired
    public AgencyController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_AGENCIES_USER')")
    public ResponseEntity<AgencyResponse> getAllAgencies() {
        try {
            var query = new FindAllAgenciesQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AgencyResponse.class)).join();

            if (response == null || response.getAgencies() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all agencies request";
            log.error(e.toString());
            return new ResponseEntity<>(new AgencyResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/active")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_AGENCIES_USER')")
    public ResponseEntity<AgencyResponse> getAllActiveAgencies() {
        try {
            var query = new FindAllActiveAgenciesQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AgencyResponse.class)).join();

            if (response == null || response.getAgencies() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all active agencies request";
            log.error(e.toString());
            return new ResponseEntity<>(new AgencyResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_AGENCIES_USER')")
    public ResponseEntity<AgencyResponse> getAgencyById(@PathVariable(value = "id") String id) {
        try {
            var query = new FindAgencyByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AgencyResponse.class)).join();

            if (response == null || response.getAgencies() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get agency by id request";
            log.error(e.toString());
            return new ResponseEntity<>(new AgencyResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/filter/{filter}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_AGENCIES_USER')")
    public ResponseEntity<AgencyResponse> searchAgenciesByFilter(@PathVariable(value = "filter") String filter) {
        try {
            var query = new SearchAgenciesQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(AgencyResponse.class)).join();

            if (response == null || response.getAgencies() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete search request";
            log.error(e.toString());
            return new ResponseEntity<>(new AgencyResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_AGENCIES_USER')")
    public ResponseEntity<PagedAgencyResponse> getAgenciesByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindAgenciesByPageQuery(page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PagedAgencyResponse.class)).join();

            if (response == null || response.getContent() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to get agencies by page request";
            log.error(e.toString());
            return new ResponseEntity<>(new PagedAgencyResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/status/active/")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_AGENCIES_USER')")
    public ResponseEntity<PagedAgencyResponse> getActiveAgenciesByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                       @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindActiveAgenciesByPageQuery(page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PagedAgencyResponse.class)).join();

            if (response == null || response.getContent() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to get active agencies by page request";
            log.error(e.toString());
            return new ResponseEntity<>(new PagedAgencyResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
