package org.sample.webmetric.click;

import org.sample.webmetric.report.ImpressionClicksMetricProjection;
import org.sample.webmetric.report.RecommendedAdvertisersMetricProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdClickRepo extends JpaRepository<AdClick, String> {

    @Query("""
            select imp.appId as appId, imp.countryCode as countryCode,
             count(imp) as impressions, count (cl) as clicks, sum(cl.revenue) as revenue
              from AdClick cl join cl.impression imp
            group by imp.appId, imp.countryCode
            order by appId
            """)
    List<ImpressionClicksMetricProjection> calculateMetrics();

    @Query(value = """
            SELECT t2.app_id appId,
                   t2.country_code countryCode,
                   group_concat(t2.advertiser_id, ',') AS advertiserIds
             FROM (SELECT t1.app_id,
                          t1.country_code,
                          t1.advertiser_id,
                          t1.revenue_per_impression,
                          ROW_NUMBER() OVER (PARTITION BY app_id, country_code ORDER BY t1.revenue_per_impression DESC) AS rank
                   FROM (SELECT i.app_id,
                                i.country_code,
                                i.advertiser_id,
                                SUM(ac.revenue) / coalesce(COUNT(i.id), 0) AS revenue_per_impression
                         FROM impression i
                                  JOIN
                              adv_click ac ON i.id = ac.impression_id
                         GROUP BY i.app_id,
                                  i.country_code,
                                  i.advertiser_id) t1) as t2
             WHERE t2.rank <= 5
             GROUP BY app_id, country_code
                """, nativeQuery = true)
    List<RecommendedAdvertisersMetricProjection> findTop5AdvByAppIdAndCountry();
}
