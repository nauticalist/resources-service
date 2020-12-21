package io.seanapse.clients.jms.services.resources.core.agency.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Immutable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "agencies")
public class Agency {
    @Id
    private String id;

    @TextIndexed
    @NotNull(message = "Agency name must be provided.")
    @Size(min=2, max=200, message = "Agency name must be minimum 2 maximum 200 characters.")
    private String agencyName;

    @TextIndexed
    @NotNull(message = "Agency name must be provided.")
    @Size(min=2, max=200, message = "Agency name must be minimum 2 maximum 200 characters.")
    private String companyTitle;

    @NotNull(message = "Email address must be provided.")
    @Email(message = "please provide a valid email address")
    private String emailAddress;

    private String phone;

    private String fax;

    private String website;

    private Address address;

    private boolean isActive;

    @Immutable
    private Date createdAt;

    @Immutable
    private String createdBy;

    private Date modifiedAt;

    private String modifiedBy;
}
