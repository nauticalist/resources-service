package io.seanapse.clients.jms.services.resources.query.api.agency.handlers;

import io.seanapse.clients.jms.services.resources.query.api.agency.dto.AgencyResponse;
import io.seanapse.clients.jms.services.resources.query.api.agency.dto.PagedAgencyResponse;
import io.seanapse.clients.jms.services.resources.query.api.agency.queries.*;

public interface AgencyQueryHandler {
    AgencyResponse getAgencyById(FindAgencyByIdQuery query);
    AgencyResponse getAllAgencies(FindAllAgenciesQuery query);
    AgencyResponse getAllActiveAgencies(FindAllActiveAgenciesQuery query);
    AgencyResponse searchAgencies(SearchAgenciesQuery query);

    PagedAgencyResponse getAgenciesByPage(FindAgenciesByPageQuery query);
    PagedAgencyResponse getActiveAgenciesByPage(FindActiveAgenciesByPageQuery query);
}
