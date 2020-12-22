package io.seanapse.clients.jms.services.resources.query.api.agency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.seanapse.clients.jms.services.resources.core.agency.models.Agency;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PagedAgencyResponse extends BaseResponse {
    private Pageable pageable;

    private List<Agency> content;

    private long totalNumberOfElements;

    public PagedAgencyResponse(String message) {
        super(message);
    }

    public PagedAgencyResponse(Pageable pageable, List<Agency> content, Long totalNumberOfElements) {
        super(null);
        this.pageable = pageable;
        this.content = content;
        this.totalNumberOfElements = totalNumberOfElements;
    }

    public List<Agency> getContent() {
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
