package io.seanapse.clients.jms.services.resources.query.api.port.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindPortByIdQuery {
    private String id;
}
