package io.seanapse.clients.jms.services.resources.query.api.vehicle.dto;

import io.seanapse.clients.jms.services.resources.core.dto.BaseResponse;
import io.seanapse.clients.jms.services.resources.core.vehicles.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleResponse extends BaseResponse {
    private List<Vehicle> vehicles;

    public VehicleResponse(String message) {
        super(message);
    }

    public VehicleResponse(List<Vehicle> vehicles) {
        super(null);
        this.vehicles = vehicles;
    }

    public VehicleResponse(String message, Vehicle vehicle) {
        super(message);
        this.vehicles = new ArrayList<>();
        this.vehicles.add(vehicle);
    }


    public VehicleResponse(Vehicle vehicle) {
        super(null);
        this.vehicles = new ArrayList<>();
        this.vehicles.add(vehicle);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
