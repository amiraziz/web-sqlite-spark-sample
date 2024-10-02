package org.sample.webmetric.click;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.sample.webmetric.report.ImpressionClicksMetricProjection;
import org.sample.webmetric.report.RecommendedAdvertisersMetric;
import org.sample.webmetric.report.RecommendedAdvertisersMetricProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdClickService {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final AdClickRepo repo;
    private final AdClicksMapper mapper;
    private final SparkSession sparkSession;

    @Transactional
    public void importFileData(MultipartFile file) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());
            String jsonFileString = new String(byteArrayInputStream.readAllBytes());
            List<AdClickDto> clicks = OBJECT_MAPPER.readValue(jsonFileString, new TypeReference<>() {
            });

            List<AdClick> entities = mapper.toEntity(clicks);

            repo.saveAll(entities);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ImpressionClicksMetricProjection> calculateMetrics() {
        return repo.calculateMetrics();
    }

    public List<RecommendedAdvertisersMetric> top5AdvByAppIdAndCountry() {
        List<RecommendedAdvertisersMetricProjection> records = repo.findTop5AdvByAppIdAndCountry();
        return records.stream().map(item -> {
            RecommendedAdvertisersMetric advMetric = new RecommendedAdvertisersMetric();
            advMetric.setAppId(item.getAppId());
            advMetric.setCountryCode(item.getCountryCode());
            advMetric.setAdvertiserIds(
                    Arrays.stream(item.getAdvertiserIds().split(","))
                            .map(Long::parseLong).collect(Collectors.toList()).subList(0, 5));
            return advMetric;
        }).collect(Collectors.toList());
    }

    @Transactional
    public AdClick save(AdClick click) {
        click.setId(UUID.randomUUID().toString());
        return repo.save(click);
    }

    public List<RecommendedAdvertisersMetric> top5AdvByAppIdAndCountrySpark() {

        Properties properties = new Properties();
        Dataset<Row> impressions = sparkSession.read().jdbc(jdbcUrl, "impression", properties);
        Dataset<Row> clicks = sparkSession.read().jdbc(jdbcUrl, "adv_click", properties);

        Dataset<Row> jointTables = clicks.join(impressions, clicks.col("impression_id").equalTo(impressions.col("id")));
        Dataset<Row> app_id__country_code__grouping =
                jointTables
                        .groupBy("app_id", "country_code")
                        .df();
        app_id__country_code__grouping.show();
        return null;
    }
}
