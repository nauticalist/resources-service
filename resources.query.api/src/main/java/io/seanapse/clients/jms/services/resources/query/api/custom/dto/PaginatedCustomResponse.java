package io.seanapse.clients.jms.services.resources.query.api.custom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.seanapse.clients.jms.services.resources.core.custom.models.Custom;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginatedCustomResponse extends BaseResponse {
    private Pageable pageable;

    private List<Custom> content;

    private long totalNumberOfElements;

    public PaginatedCustomResponse(String message) {
        super(message);
    }

    public PaginatedCustomResponse(Pageable pageable, List<Custom> content, Long totalNumberOfElements) {
        super(null);
        this.pageable = pageable;
        this.content = content;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public List<Custom> getContent() {
        return content;
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
