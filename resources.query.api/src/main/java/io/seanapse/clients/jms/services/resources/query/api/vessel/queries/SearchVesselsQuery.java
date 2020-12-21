package io.seanapse.clients.jms.services.resources.query.api.vessel.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchVesselsQuery {
    private String filter;
}
