package io.seanapse.clients.jms.services.resources.query.api.vessel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import io.seanapse.clients.jms.services.resources.core.vessel.models.Vessel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PagedVesselResponse extends BaseResponse {
    private Pageable pageable;

    private List<Vessel> vessels;

    private long totalNumberOfElements;

    public PagedVesselResponse(String message) {
        super(message);
    }

    public PagedVesselResponse(Pageable pageable, List<Vessel> vessels, Long totalNumberOfElements) {
        super(null);
        this.pageable = pageable;
        this.vessels = vessels;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public List<Vessel> getVessels() {
        return vessels;
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
