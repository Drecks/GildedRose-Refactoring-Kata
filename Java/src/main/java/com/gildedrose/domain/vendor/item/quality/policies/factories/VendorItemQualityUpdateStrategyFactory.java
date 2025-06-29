package com.gildedrose.domain.vendor.item.quality.policies.factories;

import com.gildedrose.domain.vendor.item.quality.policies.strategies.*;

/**
 * Factory for providing stateless instances of {@link VendorItemQualityUpdateStrategy}
 * implementations based on the strategy type and parameter set.
 * <p>
 * This factory abstracts the instantiation of strategy instances and ensures reuse of
 * shared singletons where appropriate.
 * </p>
 */
public final class VendorItemQualityUpdateStrategyFactory {

    /**
     * Returns a linear quality update strategy instance
     *
     * @return the shared {@link LinearQualityUpdateStrategy}
     */
    public LinearQualityUpdateStrategy GetLinearStrategy()
    {
        return LinearQualityUpdateStrategy.INSTANCE;
    }

    /**
     * Returns a dynamic quality update strategy instance
     *
     * @return the shared {@link DynamicQualityUpdateStrategy}
     */
    public DynamicQualityUpdateStrategy GetDynamicStrategy()
    {
        return DynamicQualityUpdateStrategy.INSTANCE;
    }
}
