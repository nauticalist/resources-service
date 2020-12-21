package io.seanapse.clients.jms.services.resources.query.api.agency.dto;

import io.seanapse.clients.jms.services.resources.core.agency.models.Agency;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class AgencyResponse extends BaseResponse {
    private List<Agency> agencies;

    public AgencyResponse(String message) {
        super(message);
    }

    public AgencyResponse(List<Agency> agencies) {
        super(null);
        this.agencies = agencies;
    }

    public AgencyResponse(String message, Agency agency) {
        super(message);
        this.agencies = new ArrayList<>();
        this.agencies.add(agency);
    }

    public AgencyResponse(Agency agency) {
        super(null);
        this.agencies = new ArrayList<>();
        this.agencies.add(agency);
    }

    public List<Agency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<Agency> agencies) {
        this.agencies = agencies;
    }
}
