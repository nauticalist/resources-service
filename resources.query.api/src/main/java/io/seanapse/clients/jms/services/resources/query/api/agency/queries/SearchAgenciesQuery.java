package io.seanapse.clients.jms.services.resources.query.api.agency.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchAgenciesQuery {
    private String filter;
}
