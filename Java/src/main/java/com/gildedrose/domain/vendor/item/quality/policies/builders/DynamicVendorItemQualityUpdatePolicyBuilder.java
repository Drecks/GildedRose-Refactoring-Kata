package com.gildedrose.domain.vendor.item.quality.policies.builders;

import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemDailyQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.DynamicQualityUpdateRateTimeline;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.DynamicQualityUpdateStrategyParameters;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.VendorItemQualityUpdateStrategy;

public class DynamicVendorItemQualityUpdatePolicyBuilder implements VendorItemQualityUpdatePolicyBuilder {

    private int initialRate = 0;
    private final DynamicQualityUpdateRateTimeline steps = new DynamicQualityUpdateRateTimeline();
    private Integer qualityAfterExpiration = null;

    public DynamicVendorItemQualityUpdatePolicyBuilder withInitialDailyChangeRate(int initialRate) {
        this.initialRate = initialRate;
        return this;
    }

    public DynamicVendorItemQualityUpdatePolicyBuilder withRateBelowExpirationDays(int rate, int expirationDays) {
        steps.setRateBelowExpirationDays(rate, expirationDays);
        return this;
    }

    public DynamicVendorItemQualityUpdatePolicyBuilder withQualityAfterExpiration(int quality) {
        qualityAfterExpiration = quality;
        return this;
    }

    public VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory) {
        DynamicQualityUpdateStrategyParameters parameters = new DynamicQualityUpdateStrategyParameters(initialRate, steps, qualityAfterExpiration);
        VendorItemQualityUpdateStrategy<DynamicQualityUpdateStrategyParameters> strategy = factory.GetDynamicStrategy();
        return new VendorItemDailyQualityUpdatePolicy<>(strategy, parameters);
    }
}
