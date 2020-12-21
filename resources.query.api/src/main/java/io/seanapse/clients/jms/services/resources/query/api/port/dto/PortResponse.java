package io.seanapse.clients.jms.services.resources.query.api.port.dto;

import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import io.seanapse.clients.jms.services.resources.core.port.models.Port;

import java.util.ArrayList;
import java.util.List;

public class PortResponse extends BaseResponse {
    private List<Port> ports;

    public PortResponse(String message) {
        super(message);
    }

    public PortResponse(List<Port> ports) {
        super(null);
        this.ports = ports;
    }

    public PortResponse(String message, Port port) {
        super(message);
        this.ports = new ArrayList<>();
        this.ports.add(port);
    }

    public PortResponse(Port port) {
        super(null);
        this.ports = new ArrayList<>();
        this.ports.add(port);
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }
}
