package io.seanapse.clients.jms.services.resources.query.api.port.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import io.seanapse.clients.jms.services.resources.core.port.models.Port;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginatedPortResponse extends BaseResponse {
    private Pageable pageable;

    private List<Port> content;

    private long totalNumberOfElements;

    public PaginatedPortResponse(String message) {
        super(message);
    }

    public PaginatedPortResponse(Pageable pageable, List<Port> content, Long totalNumberOfElements) {
        super(null);
        this.pageable = pageable;
        this.content = content;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public List<Port> getContent() {
        return content;
    }

    @JsonProperty("totalCount")
    public long getTotalCount() {
        return totalNumberOfElements;
    }

    @JsonProperty("totalPages")
    public int getTotalPages() {
        double totalNumberOfPages = Math.floor(((double) totalNumberOfElements + (double) pageable.getPageSize() - 1) / (double) pageable.getPageSize());
        return (int) totalNumberOfPages;
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
