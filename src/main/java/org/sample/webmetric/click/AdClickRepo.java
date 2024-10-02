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
            select imp.app_id                                   as appId,
                   imp.country_code                             as countryCode,
                   sum(cl.revenue) / count(imp.id)              as ratio,
                   group_concat(distinct imp.advertiser_id)     as advertiserIds
                from adv_click cl
                         join impression imp on cl.impression_id = imp.id
                group by imp.app_id, imp.country_code
                order by ratio desc
                """, nativeQuery = true)
    List<RecommendedAdvertisersMetricProjection> findTop5AdvByAppIdAndCountry();
}
