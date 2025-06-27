package com.gildedrose.domain.vendor.item.quality.policies.factories;

import com.gildedrose.domain.vendor.item.quality.policies.strategies.*;

public class VendorItemQualityUpdateStrategyFactory {
    private final DynamicQualityUpdateStrategy dynamic;
    private final LinearQualityUpdateStrategy linear;

    public VendorItemQualityUpdateStrategyFactory() {
        this.dynamic = new DynamicQualityUpdateStrategy();
        this.linear = new LinearQualityUpdateStrategy();
    }

    public VendorItemQualityUpdateStrategy<LinearQualityUpdateStrategyParameters> GetLinearStrategy()
    {
        return this.linear;
    }
    public VendorItemQualityUpdateStrategy<DynamicQualityUpdateStrategyParameters> GetDynamicStrategy()
    {
        return this.dynamic;
    }
}
