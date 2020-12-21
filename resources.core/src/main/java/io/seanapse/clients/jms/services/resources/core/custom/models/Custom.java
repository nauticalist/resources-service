package io.seanapse.clients.jms.services.resources.core.custom.models;

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
@Document(collection = "customs")
public class Custom {
    @Id
    private String id;

    @TextIndexed
    @NotNull(message = "Custom office name must be provided.")
    @Size(min=2, max=200, message = "Custom office name must be minimum 2 maximum 200 characters.")
    private String customOffice;

    private Date createdAt;

    private String createdBy;

    private Date modifiedAt;

    private String modifiedBy;
}
