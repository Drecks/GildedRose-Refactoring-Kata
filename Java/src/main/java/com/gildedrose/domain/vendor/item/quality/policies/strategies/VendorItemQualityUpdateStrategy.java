package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import com.gildedrose.domain.vendor.item.VendorItem;

/**
 * Defines a strategy for updating the quality of a {@link VendorItem}, using
 * a provided set of parameters.
 * <p>
 * Implementations of this interface encapsulate a specific algorithm or rule
 * for adjusting an item's quality, such as linear or dynamic rate changes over time and expiration-based
 * effects.
 * </p>
 *
 * <p>
 * This interface supports generic parameterization via {@code TParameters},
 * allowing each strategy to receive structured inputs specific to its logic.
 * </p>
 *
 * <p>
 * Typical usage:
 * </p>
 * <pre>{@code
 * VendorItemQualityUpdateStrategy<LinearQualityUpdateStrategyParameters> strategy = new LinearQualityUpdateStrategy();
 * strategy.execute(item, parameters);
 * }</pre>
 *
 * @param <TParameters> the type of parameters used to control the strategy's behavior
 *
 * @see VendorItem
 * @see LinearQualityUpdateStrategy
 * @see DynamicQualityUpdateStrategy
 */
public interface VendorItemQualityUpdateStrategy<TParameters> {
    /**
     * Updates the given {@link VendorItem}'s quality according to the strategy's rules
     * and the provided parameters.
     *
     * @param item   the item to update; must not be {@code null}
     * @param params the parameters controlling the update logic; must not be {@code null}
     */
    void execute(VendorItem item, TParameters params);
}
