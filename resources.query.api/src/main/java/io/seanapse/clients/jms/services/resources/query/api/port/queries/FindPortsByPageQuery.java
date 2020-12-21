package io.seanapse.clients.jms.services.resources.query.api.port.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FindPortsByPageQuery {
    private int page;
    private int size;
}
