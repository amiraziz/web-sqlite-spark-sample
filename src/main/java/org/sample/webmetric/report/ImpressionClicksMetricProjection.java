package org.sample.webmetric.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ImpressionClicksMetricProjection {

    @JsonProperty("app_id")
    Long getAppId();

    @JsonProperty("country_code")
    String getCountryCode();

    Long getImpressions();

    Long getClicks();

    BigDecimal getRevenue();
}
