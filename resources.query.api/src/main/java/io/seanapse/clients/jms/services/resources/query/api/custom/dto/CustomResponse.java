package io.seanapse.clients.jms.services.resources.query.api.custom.dto;

import io.seanapse.clients.jms.services.resources.core.custom.models.Custom;
import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class CustomResponse extends BaseResponse {
    private List<Custom> customs;

    public CustomResponse(String message) {
        super(message);
    }

    public CustomResponse(List<Custom> customs) {
        super(null);
        this.customs = customs;
    }

    public CustomResponse(String message, Custom custom) {
        super(message);
        this.customs = new ArrayList<>();
        this.customs.add(custom);
    }

    public CustomResponse(Custom custom) {
        super(null);
        this.customs = new ArrayList<>();
        this.customs.add(custom);
    }

    public List<Custom> getCustoms() {
        return customs;
    }

    public void setCustoms(List<Custom> customs) {
        this.customs = customs;
    }
}
