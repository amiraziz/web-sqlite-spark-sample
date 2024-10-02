package org.sample.webmetric.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RecommendedAdvertisersMetricProjection {

    @JsonProperty("app_id")
    Long getAppId();

    @JsonProperty("country_code")
    String getCountryCode();

    @Value("#{target['advertiserIds'].split(',')}")
    String getAdvertiserIds();
}
