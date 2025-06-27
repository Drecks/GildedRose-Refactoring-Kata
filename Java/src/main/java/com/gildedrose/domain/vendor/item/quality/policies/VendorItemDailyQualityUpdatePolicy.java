package com.gildedrose.domain.vendor.item.quality.policies;

import com.gildedrose.domain.vendor.item.VendorItem;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.VendorItemQualityUpdateStrategy;

import java.util.Objects;
public class VendorItemDailyQualityUpdatePolicy<TParameters> implements VendorItemQualityUpdatePolicy {
    private final VendorItemQualityUpdateStrategy<TParameters> strategy;
    private final TParameters parameters;

    public VendorItemDailyQualityUpdatePolicy(
        VendorItemQualityUpdateStrategy<TParameters> strategy,
        TParameters parameters
    ) {
        this.strategy = Objects.requireNonNull(strategy, "strategy must not be null");
        this.parameters = Objects.requireNonNull(parameters, "parameters must not be null");
    }

    public void apply(VendorItem item) {
        strategy.update(item, parameters);
    }

    @Override
    public String toString() {
        return "VendorItemQualityUpdatePolicy(strategy=" + strategy.getClass().getName() + ")";
    }
}
