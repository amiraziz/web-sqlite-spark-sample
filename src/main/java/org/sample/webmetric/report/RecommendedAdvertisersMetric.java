package org.sample.webmetric.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendedAdvertisersMetric {
    @JsonProperty("app_id")
    private Long appId;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("recommended_advertiser_ids")
    private List<Long> advertiserIds;
}
