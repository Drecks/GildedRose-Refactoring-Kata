package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import com.gildedrose.domain.vendor.item.VendorItem;

/**
 * A {@link VendorItemQualityUpdateStrategy} that adjusts a vendor item's quality
 * using a fixed linear rate.
 * <p>
 * This strategy increases or decreases an item's quality by applying either a regular
 * update rate or an alternate rate used specifically after expiration. It is suitable for
 * items that follow a simple rule like "degrade by 1 before expiration, degrade by 2 after."
 * </p>
 *
 * <p>
 * This class is stateless and implemented as a singleton accessible via {@link #INSTANCE}.
 * </p>
 *
 * <pre>{@code
 * var strategy = LinearQualityUpdateStrategy.INSTANCE;
 * strategy.execute(item, new LinearQualityUpdateStrategyParameters(-1, -2));
 * }</pre>
 *
 * @see VendorItem
 * @see LinearQualityUpdateStrategyParameters
 * @see VendorItemQualityUpdateStrategy
 */
public final class LinearQualityUpdateStrategy implements VendorItemQualityUpdateStrategy<LinearQualityUpdateStrategyParameters> {
    /** Shared singleton instance of the strategy */
    public static final LinearQualityUpdateStrategy INSTANCE = new LinearQualityUpdateStrategy();

    private LinearQualityUpdateStrategy() {}

    @Override
    public void execute(VendorItem item, LinearQualityUpdateStrategyParameters params) {
        assert item != null;
        assert params != null;
        int rate = item.isExpired() ? params.getExpiredRate(): params.getRate();
        int newQuality = item.getQuality() + rate;
        item.setQuality(newQuality);
    }
}
