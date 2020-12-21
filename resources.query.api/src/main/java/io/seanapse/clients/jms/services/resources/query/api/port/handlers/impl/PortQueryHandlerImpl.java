package io.seanapse.clients.jms.services.resources.query.api.port.handlers.impl;

import io.seanapse.clients.jms.services.resources.query.api.port.dto.PaginatedPortResponse;
import io.seanapse.clients.jms.services.resources.query.api.port.dto.PortResponse;
import io.seanapse.clients.jms.services.resources.query.api.port.handlers.PortQueryHandler;
import io.seanapse.clients.jms.services.resources.query.api.port.queries.*;
import io.seanapse.clients.jms.services.resources.query.api.port.repositories.PortRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PortQueryHandlerImpl implements PortQueryHandler {
    private final PortRepository portRepository;

    @Autowired
    public PortQueryHandlerImpl(PortRepository portRepository) {
        this.portRepository = portRepository;
    }

    @Override
    @QueryHandler
    public PortResponse getPortById(FindPortByIdQuery query) {
        var port = portRepository.findById(query.getId());
        return port.isPresent() ? new PortResponse(port.get()) : null;
    }

    @Override
    @QueryHandler
    public PortResponse getAllPorts(FindAllPortsQuery query) {
        var ports = new ArrayList<>(portRepository.findAll());
        return new PortResponse(ports);
    }

    @Override
    @QueryHandler
    public PortResponse searchPorts(SearchPortsQuery query) {
        var ports = new ArrayList<>(portRepository.findByFilterRegex(query.getFilter()));
        return new PortResponse(ports);
    }

    @Override
    @QueryHandler
    public PaginatedPortResponse getPortsByPage(FindPortsByPageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize(), Sort.by("portName").ascending());
        var totalNumberOfPorts = portRepository.count();
        var ports = new ArrayList<>(portRepository.findPortsByPage(pageRequest));
        return new PaginatedPortResponse(pageRequest, ports, totalNumberOfPorts);
    }
}
