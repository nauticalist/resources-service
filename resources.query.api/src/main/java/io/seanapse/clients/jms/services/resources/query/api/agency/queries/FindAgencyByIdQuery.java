package io.seanapse.clients.jms.services.resources.query.api.agency.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAgencyByIdQuery {
    private String id;
}
