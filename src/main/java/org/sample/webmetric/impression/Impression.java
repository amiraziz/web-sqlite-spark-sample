package org.sample.webmetric.impression;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "IMPRESSION")
@Entity
@Getter
@Setter
public class Impression {

    @Id
    @Column(name = "ID", updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
    private String id;

    @NotNull
    @JsonProperty("app_id")
    private Long appId;
    @NotNull
    @JsonProperty("advertiser_id")
    private Long advertiserId;

    @JsonProperty("country_code")
    private String countryCode;
}
