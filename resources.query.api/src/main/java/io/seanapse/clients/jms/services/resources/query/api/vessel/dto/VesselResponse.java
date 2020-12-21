package io.seanapse.clients.jms.services.resources.query.api.vessel.dto;

import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import io.seanapse.clients.jms.services.resources.core.vessel.models.Vessel;

import java.util.ArrayList;
import java.util.List;

public class VesselResponse extends BaseResponse {
    private List<Vessel> vessels;

    public VesselResponse(String message) {
        super(message);
    }

    public VesselResponse(List<Vessel> vessels) {
        super(null);
        this.vessels = vessels;
    }

    public VesselResponse(String message, Vessel vessel) {
        super(message);
        this.vessels = new ArrayList<>();
        this.vessels.add(vessel);
    }

    public VesselResponse(Vessel vessel) {
        super(null);
        this.vessels = new ArrayList<>();
        this.vessels.add(vessel);
    }

    public List<Vessel> getVessels() {
        return vessels;
    }

    public void setVessels(List<Vessel> vessels) {
        this.vessels = vessels;
    }
}
