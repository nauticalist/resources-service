package io.seanapse.clients.jms.services.resources.query.api.vessel.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindVesselsByPageQuery {
    private int page;
    private int size;
}
