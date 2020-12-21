package io.seanapse.clients.jms.services.resources.query.api.port.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchPortsQuery {
    private String filter;
}
