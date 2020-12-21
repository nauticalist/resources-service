package io.seanapse.clients.jms.services.resources.query.api.agency.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindActiveAgenciesByPageQuery {
    private int page;
    private int size;
}
