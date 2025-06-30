package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import com.gildedrose.domain.vendor.item.VendorItem;

/**
 * A {@link VendorItemQualityUpdateStrategy} implementation that dynamically adjusts
 * an item's quality based on its remaining expiration days and optional post-expiration behavior.
 * <p>
 * This strategy supports two modes:
 * </p>
 * <ul>
 *   <li>If the item is expired and a specific quality is defined via
 *       {@code qualityAfterExpiration}, that value is directly applied.</li>
 *   <li>Otherwise, a change rate is retrieved from a {@link DynamicQualityUpdateRateTimeline} based on the item's
 *       expiration days. If no specific rate is defined for the day, a fallback initial rate is used.
 *       The rate is then applied additively to the item's current quality.</li>
 * </ul>
 *
 * <p>
 * This class is stateless and provided as a shared singleton instance via {@link #INSTANCE}.
 * </p>
 *
 * <pre>{@code
 * var strategy = DynamicQualityUpdateStrategy.INSTANCE;
 * strategy.execute(item, parameters);
 * }</pre>
 *
 * @see VendorItem
 * @see DynamicQualityUpdateStrategyParameters
 * @see VendorItemQualityUpdateStrategy
 */
public final class DynamicQualityUpdateStrategy implements VendorItemQualityUpdateStrategy<DynamicQualityUpdateStrategyParameters> {
    /**
     * Shared stateless instance of this strategy.
     */
    public static final DynamicQualityUpdateStrategy INSTANCE = new DynamicQualityUpdateStrategy();
    private DynamicQualityUpdateStrategy() {}
    @Override
    public void execute(VendorItem item, DynamicQualityUpdateStrategyParameters params) {
        assert item != null;
        assert params != null;

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
