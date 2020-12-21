package io.seanapse.clients.jms.services.resources.query.api.custom.handlers.impl;

import io.seanapse.clients.jms.services.resources.query.api.custom.dto.CustomResponse;
import io.seanapse.clients.jms.services.resources.query.api.custom.dto.PaginatedCustomResponse;
import io.seanapse.clients.jms.services.resources.query.api.custom.handlers.CustomQueryHandler;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.FindAllCustomsQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.FindCustomByIdQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.FindCustomsByPageQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.SearchCustomsQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.repositories.CustomRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomQueryHandlerImpl implements CustomQueryHandler {
    private final CustomRepository customRepository;

    @Autowired
    public CustomQueryHandlerImpl(CustomRepository customRepository) {
        this.customRepository = customRepository;
    }

    @Override
    @QueryHandler
    public CustomResponse getCustomById(FindCustomByIdQuery query) {
        var custom = customRepository.findById(query.getId());
        return custom.map(CustomResponse::new).orElse(null);
    }

    @Override
    @QueryHandler
    public CustomResponse getAllCustoms(FindAllCustomsQuery query) {
        var customs = new ArrayList<>(customRepository.findAll());
        return new CustomResponse(customs);
    }

    @Override
    @QueryHandler
    public CustomResponse searchCustoms(SearchCustomsQuery query) {
        var customs = new ArrayList<>(customRepository.findByFilterRegex(query.getFilter()));
        return new CustomResponse(customs);
    }

    @Override
    @QueryHandler
    public PaginatedCustomResponse getCustomsByPage(FindCustomsByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("customOffice").ascending());
        var totalNumberOfCustoms = customRepository.count();
        var customs = new ArrayList<>(customRepository.findCustomsByPage(pageRequest));
        return new PaginatedCustomResponse(pageRequest, customs, totalNumberOfCustoms);
    }
}
