package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import java.util.Optional;

public class DynamicQualityUpdateStrategyParameters {
    private final int initialUpdateRate;
    private final DynamicQualityUpdateRateTimeline rateTimeline;
    private final Integer qualityAfterExpiration;

    public DynamicQualityUpdateStrategyParameters(int initialUpdateRate, DynamicQualityUpdateRateTimeline timeline, Integer qualityAfterExpiration) {
        this.initialUpdateRate = initialUpdateRate;
        this.rateTimeline = timeline;
        this.qualityAfterExpiration = qualityAfterExpiration;
    }

    public int getInitialRate() {
        return initialUpdateRate;
    }

    public DynamicQualityUpdateRateTimeline getRateTimeline() {
        return rateTimeline;
    }

    public Optional<Integer> getQualityAfterExpiration() {
        return Optional.ofNullable(qualityAfterExpiration);
    }
}
