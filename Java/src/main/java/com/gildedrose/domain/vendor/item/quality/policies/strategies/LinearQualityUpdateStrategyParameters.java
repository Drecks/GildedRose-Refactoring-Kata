package com.gildedrose.domain.vendor.item.quality.policies.strategies;

public class LinearQualityUpdateStrategyParameters {
    public final int rate;
    public final int expiredRate;
    public LinearQualityUpdateStrategyParameters(int initialRate, int expiredRate) {
        this.rate = initialRate;
        this.expiredRate = expiredRate;
    }
}
