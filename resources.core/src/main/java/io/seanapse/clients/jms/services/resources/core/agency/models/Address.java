package io.seanapse.clients.jms.services.resources.core.agency.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String addressLine1;

    private String addressLine2;

    private String city;

    private String region;

    private String postCode;

    private String country;
}