package io.seanapse.clients.jms.services.resources.core.vehicles.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "vehicles")
public class Vehicle {
    @Id
    private String id;

    @Indexed(unique = true)
    @TextIndexed
    @NotNull(message = "License plate must be provided.")
    @Size(min=2, max=200, message = "License plate must be minimum 2 maximum 200 characters.")
    private String identificationPlate;

    private int year;

    private String make;

    private String model;

    private String color;

    private String driver;

    private boolean isActive;

    private Date createdAt;

    private String createdBy;

    private Date modifiedAt;

    private String modifiedBy;
}
