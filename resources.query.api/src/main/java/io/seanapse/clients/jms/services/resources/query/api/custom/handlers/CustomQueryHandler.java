package io.seanapse.clients.jms.services.resources.query.api.custom.handlers;

import io.seanapse.clients.jms.services.resources.query.api.custom.dto.CustomResponse;
import io.seanapse.clients.jms.services.resources.query.api.custom.dto.PaginatedCustomResponse;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.FindAllCustomsQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.FindCustomByIdQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.FindCustomsByPageQuery;
import io.seanapse.clients.jms.services.resources.query.api.custom.queries.SearchCustomsQuery;

public interface CustomQueryHandler {
    CustomResponse getCustomById(FindCustomByIdQuery query);
    CustomResponse getAllCustoms(FindAllCustomsQuery query);
    CustomResponse searchCustoms(SearchCustomsQuery query);
    PaginatedCustomResponse getCustomsByPage(FindCustomsByPageQuery query);
}
