package org.sample.webmetric.click;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdClickDto {

    private String id;

    @JsonProperty("impression_id")
    private String impressionId;
    private BigDecimal revenue;
}
