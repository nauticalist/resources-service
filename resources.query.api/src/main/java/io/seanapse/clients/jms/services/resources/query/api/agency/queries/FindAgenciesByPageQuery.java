package io.seanapse.clients.jms.services.resources.query.api.agency.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAgenciesByPageQuery {
    private int page;
    private int size;
}
