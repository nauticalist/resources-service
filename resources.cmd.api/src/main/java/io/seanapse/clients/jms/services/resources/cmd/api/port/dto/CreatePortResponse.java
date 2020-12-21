package io.seanapse.clients.jms.services.resources.cmd.api.port.dto;

import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;


public class CreatePortResponse extends BaseResponse {
    private String id;

    public CreatePortResponse(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
