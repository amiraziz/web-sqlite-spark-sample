package org.sample.webmetric.report;

import lombok.RequiredArgsConstructor;
import org.sample.webmetric.click.AdClickService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final AdClickService adClickService;

    @GetMapping("/1")
    public List<ImpressionClicksMetricProjection> calculateMetrics() {
        return adClickService.calculateMetrics();
    }

    @GetMapping("/2")
    public List<RecommendedAdvertisersMetric> top5AdvByAppIdAndCountry() {
        return adClickService.top5AdvByAppIdAndCountry();
    }

    @PostMapping("/2")
    public List<RecommendedAdvertisersMetric> top5AdvByAppIdAndCountrySpark() {
        return adClickService.top5AdvByAppIdAndCountrySpark();
    }
}
