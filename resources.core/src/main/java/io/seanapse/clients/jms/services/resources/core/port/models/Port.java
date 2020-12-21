package io.seanapse.clients.jms.services.resources.core.port.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "ports")
public class Port {
    @Id
    private String id;

    @TextIndexed
    @NotNull(message = "Port name must be provided.")
    @Size(min=2, max=100, message = "Port name must be minimum 2 maximum 100 characters.")
    private String portName;

    @Valid
    @NotNull(message = "please provide account credentials")
    private Location location;

    private Date createdAt;

    private String createdBy;

    private Date modifiedAt;

    private String modifiedBy;
}
