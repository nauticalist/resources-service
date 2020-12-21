package io.seanapse.clients.jms.services.resources.core.port.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @NotNull(message = "Country must be provided.")
    @Size(min=2, max=100, message = "Country must be minimum 2 maximum 100 characters.")
    private String country;

    @Indexed(unique = true)
    @NotNull(message = "Ports UN location code must be provided.")
    @Size(min=2, max=100, message = "Ports UN location code must be minimum 2 maximum 100 characters.")
    private String unLoCode;

    @DecimalMin(value = "-90.0", inclusive = false)
    @DecimalMax(value = "90.0", inclusive = false)
    private double latitude;

    @DecimalMin(value = "-180.0", inclusive = false)
    @DecimalMax(value = "180.0", inclusive = false)
    private double longitude;
}
