package io.seanapse.clients.jms.services.resources.query.api.vessel.handlers;

import io.seanapse.clients.jms.services.resources.query.api.vessel.dto.PagedVesselResponse;
import io.seanapse.clients.jms.services.resources.query.api.vessel.dto.VesselResponse;
import io.seanapse.clients.jms.services.resources.query.api.vessel.queries.*;

public interface VesselQueryHandler {
    VesselResponse getVesselById(FindVesselByIdQuery query);

    VesselResponse getAllVessels(FindAllVesselsQuery query);

    VesselResponse getActiveVessels(FindActiveVesselsQuery query);

    VesselResponse searchVessels(SearchVesselsQuery query);

    PagedVesselResponse getVesselsByPage(FindVesselsByPageQuery query);

    PagedVesselResponse getActiveVesselsByPage(FindActiveVesselsByPageQuery query);
}
