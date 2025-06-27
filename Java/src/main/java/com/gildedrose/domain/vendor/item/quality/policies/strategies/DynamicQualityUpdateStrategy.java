package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import com.gildedrose.domain.vendor.item.VendorItem;

public class DynamicQualityUpdateStrategy implements VendorItemQualityUpdateStrategy<DynamicQualityUpdateStrategyParameters> {
    @Override
    public void update(VendorItem item, DynamicQualityUpdateStrategyParameters params) {

        if(item.isExpired() && params.getQualityAfterExpiration().isPresent())
        {
            int quality = params.getQualityAfterExpiration().get();
            item.setQuality(quality);
        }
        else
        {
            int changeRate = params.getRateTimeline()
                .getRate(item.getExpirationDays())
                .orElse(params.getInitialRate());
            item.setQuality(item.getQuality() + changeRate);
        }
    }
}
