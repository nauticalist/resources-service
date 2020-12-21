package io.seanapse.clients.jms.services.resources.query.api.vehicle.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchVehiclesQuery {
    private String filter;
}
