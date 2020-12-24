package io.seanapse.clients.jms.services.resources.query.api.custom.controllers;

import io.seanapse.clients.jms.services.resources.query.api.custom.dto.CustomResponse;
import io.seanapse.clients.jms.services.resources.query.api.custom.dto.PaginatedCustomResponse;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.FindAllCustomsQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.FindCustomByIdQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.FindCustomsByPageQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.SearchCustomsQuery;
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
@RequestMapping(path = "/api/v1/resources/customs")
public class CustomController {
    private final QueryGateway queryGateway;

    @Autowired
    public CustomController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_CUSTOMS_USER')")
    public ResponseEntity<CustomResponse> getAllCustoms() {
        try {
            var query = new FindAllCustomsQuery();
            var response = queryGateway.query(query, ResponseTypes.instanceOf(CustomResponse.class)).join();

            if (response == null || response.getCustoms() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all customs request";
            log.error(e.toString());
            return new ResponseEntity<>(new CustomResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_CUSTOMS_USER')")
    public ResponseEntity<CustomResponse> getCustomById(@PathVariable(value = "id") String id) {
        try {
            var query = new FindCustomByIdQuery(id);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(CustomResponse.class)).join();

            if (response == null || response.getCustoms() == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get custom by id request";
            log.error(e.toString());
            return new ResponseEntity<>(new CustomResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/filter/{filter}")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_CUSTOMS_USER')")
    public ResponseEntity<CustomResponse> searchCustomByFilter(@PathVariable(value = "filter") String filter) {
        try {
            var query = new SearchCustomsQuery(filter);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(CustomResponse.class)).join();

            if (response == null || response.getCustoms() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete search request";
            log.error(e.toString());
            return new ResponseEntity<>(new CustomResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_RESOURCES_CUSTOMS_USER')")
    public ResponseEntity<PaginatedCustomResponse> getCustomsByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        try {
            var query = new FindCustomsByPageQuery(page, size);
            var response = queryGateway.query(query, ResponseTypes.instanceOf(PaginatedCustomResponse.class)).join();

            if (response == null || response.getContent() == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to get customs by page request";
            log.error(e.toString());
            return new ResponseEntity<>(new PaginatedCustomResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
