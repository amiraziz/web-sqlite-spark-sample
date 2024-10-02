package org.sample.webmetric.impression;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImpressionDto {

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
