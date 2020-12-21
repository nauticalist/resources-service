package io.seanapse.clients.jms.services.resources.query.api.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import io.seanapse.clients.jms.services.resources.core.vehicles.model.Vehicle;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PagedVehicleRespone extends BaseResponse {
    private Pageable pageable;

    private List<Vehicle> vehicles;

    private long totalNumberOfElements;

    public PagedVehicleRespone(String message) {
        super(message);
    }

    public PagedVehicleRespone(Pageable pageable, List<Vehicle> vehicles, Long totalNumberOfElements) {
        super(null);
        this.pageable = pageable;
        this.vehicles = vehicles;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @JsonProperty("totalCount")
    public long getTotalCount() {
        return totalNumberOfElements;
    }

    @JsonProperty("totalPages")
    public int getTotalPages() {
        return Math.toIntExact(totalNumberOfElements/pageable.getPageSize()+1);
    }

    @JsonProperty("currentPage")
    public int getCurrentPage() {
        return pageable.getPageNumber() + 1;
    }

    @JsonProperty("pageSize")
    public int getPageSize() {
        return pageable.getPageSize();
    }
}
