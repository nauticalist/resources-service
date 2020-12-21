package io.seanapse.clients.jms.services.resources.core.vessel.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "vessels")
public class Vessel {
    @Id
    private String id;

    @TextIndexed
    @NotNull(message = "Vessel name must be provided.")
    @Size(min=2, max=200, message = "Vessel name must be minimum 2 maximum 200 characters.")
    private String vesselName;

    @NotNull(message = "No vessel type was supplied.")
    private VesselType vesselType;

    private String flag;

    private int builtYear;

    private int grossTonnage;

    private int imoNumber;

    private boolean isActive;

    private Date createdAt;

    private String createdBy;

    private Date modifiedAt;

    private String modifiedBy;
}
