package io.seanapse.clients.jms.services.resources.query.api.custom.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindCustomsByPageQuery {
    private int page;
    private int size;
}
