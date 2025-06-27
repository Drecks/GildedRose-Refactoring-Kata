package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import com.gildedrose.domain.vendor.item.VendorItem;

public class LinearQualityUpdateStrategy implements VendorItemQualityUpdateStrategy<LinearQualityUpdateStrategyParameters> {
    @Override
    public void update(VendorItem item, LinearQualityUpdateStrategyParameters params) {
        int rate = item.isExpired() ? params.expiredRate : params.rate;
        int newQuality = item.getQuality() + rate;
        item.setQuality(newQuality);
    }
}
