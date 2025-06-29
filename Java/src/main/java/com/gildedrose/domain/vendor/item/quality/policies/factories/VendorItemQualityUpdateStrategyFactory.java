package com.gildedrose.domain.vendor.item.quality.policies.factories;

import com.gildedrose.domain.vendor.item.quality.policies.strategies.*;

public class VendorItemQualityUpdateStrategyFactory {
    public VendorItemQualityUpdateStrategy<LinearQualityUpdateStrategyParameters> GetLinearStrategy()
    {
        return LinearQualityUpdateStrategy.INSTANCE;
    }
    public VendorItemQualityUpdateStrategy<DynamicQualityUpdateStrategyParameters> GetDynamicStrategy()
    {
        return DynamicQualityUpdateStrategy.INSTANCE;
    }
}
