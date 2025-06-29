package com.gildedrose.domain.vendor.item.quality.policies;

import com.gildedrose.domain.vendor.item.VendorItem;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.VendorItemQualityUpdateStrategy;

/**
 * A {@link VendorItemQualityUpdatePolicy} implementation that delegates quality update logic
 * to a parameterized {@link VendorItemQualityUpdateStrategy}.
 * <p>
 * This class allows encapsulating reusable update behavior by combining a strategy implementation
 * with its associated parameters. The policy can be applied to a {@link VendorItem} to update its quality
 * according to the strategy's rules.
 * </p>
 *
 * <p>
 * This implementation assumes that all dependencies (strategy, parameters, and item) are non-null
 * and uses assertions to enforce that in development or test environments.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>{@code
 * var policy = new StrategyBasedVendorItemQualityUpdatePolicy<>(
 *     new LinearQualityUpdateStrategy(),
 *     new LinearQualityUpdateStrategy(initialRate = 1, expiredRate = 2)
 * );
 *
 * policy.apply(item);
 * }</pre>
 *
 * @param <TParameters> the type of parameters used by the strategy
 *
 * @see VendorItemQualityUpdatePolicy
 * @see VendorItemQualityUpdateStrategy
 */
public final class StrategyBasedVendorItemQualityUpdatePolicy<TParameters> implements VendorItemQualityUpdatePolicy {
    private final VendorItemQualityUpdateStrategy<TParameters> strategy;
    private final TParameters parameters;

    /**
     * Constructs a new strategy-based quality update policy.
     *
     * @param strategy   the quality update strategy to delegate to; must not be {@code null}
     * @param parameters the parameters used by the strategy; must not be {@code null}
     */
    public StrategyBasedVendorItemQualityUpdatePolicy(
        VendorItemQualityUpdateStrategy<TParameters> strategy,
        TParameters parameters
    ) {
        assert strategy != null;
        assert parameters != null;
        this.strategy = strategy;
        this.parameters = parameters;
    }

    @Override
    public void apply(VendorItem item) {
        assert item != null;
        strategy.execute(item, parameters);
    }

    @Override
    public String toString() {
        return "StrategyBasedVendorItemQualityUpdatePolicy{" +
            "strategy=" + strategy.getClass().getSimpleName() +
            ", parameters=" + parameters +
            '}';
    }
}
