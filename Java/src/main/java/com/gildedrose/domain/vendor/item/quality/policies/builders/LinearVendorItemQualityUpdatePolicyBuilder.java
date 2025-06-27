package com.gildedrose.domain.vendor.item.quality.policies.builders;

import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemDailyQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.LinearQualityUpdateStrategyParameters;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.VendorItemQualityUpdateStrategy;

public class LinearVendorItemQualityUpdatePolicyBuilder implements VendorItemQualityUpdatePolicyBuilder {

    private int initialRate = 0;
    private int expiredRate = 0;
    public LinearVendorItemQualityUpdatePolicyBuilder withDailyChangeRate(int dailyRate) {
        this.initialRate = dailyRate;
        return this;
    }
    public LinearVendorItemQualityUpdatePolicyBuilder withDailyChangeRateAfterExpiration(int expiredRate) {
        this.expiredRate = expiredRate;
        return this;
    }
    public VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory)
    {
        LinearQualityUpdateStrategyParameters parameters = new LinearQualityUpdateStrategyParameters(initialRate, expiredRate);
        VendorItemQualityUpdateStrategy<LinearQualityUpdateStrategyParameters> strategy = factory.GetLinearStrategy();
        return new VendorItemDailyQualityUpdatePolicy<>(strategy, parameters);
    }
}
