package io.seanapse.clients.jms.services.resources.query.api.port.handlers;

import io.seanapse.clients.jms.services.resources.query.api.port.dto.PaginatedPortResponse;
import io.seanapse.clients.jms.services.resources.query.api.port.dto.PortResponse;
import io.seanapse.clients.jms.services.resources.query.api.port.queries.*;

public interface PortQueryHandler {
    PortResponse getPortById(FindPortByIdQuery query);

    PortResponse getAllPorts(FindAllPortsQuery query);

    PortResponse searchPorts(SearchPortsQuery query);

    PaginatedPortResponse getPortsByPage(FindPortsByPageQuery query);
}
