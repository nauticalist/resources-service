package io.seanapse.clients.jms.services.resources.query.api.vehicle.handlers;

import io.seanapse.clients.jms.services.resources.query.api.vehicle.dto.PagedVehicleRespone;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.dto.VehicleResponse;
import io.seanapse.clients.jms.services.resources.query.api.vehicle.queries.*;

public interface VehicleQueryHandler {
    VehicleResponse getVehicleById(FindVehicleByIdQuery query);

    VehicleResponse getAllVehicles(FindAllVehiclesQuery query);

    VehicleResponse getActiveVehicles(FindActiveVehiclesQuery query);

    VehicleResponse searchVehicles(SearchVehiclesQuery query);

    PagedVehicleRespone getVehiclesByPage(FindVehiclesByPageQuery query);

    PagedVehicleRespone getActiveVehiclesByPage(FindActiveVehiclesByPageQuery query);
}
