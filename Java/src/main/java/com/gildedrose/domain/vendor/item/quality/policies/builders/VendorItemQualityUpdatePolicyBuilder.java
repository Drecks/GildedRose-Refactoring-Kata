package com.gildedrose.domain.vendor.item.quality.policies.builders;

import com.gildedrose.domain.vendor.item.quality.policies.StrategyBasedVendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.NoVendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.*;

/**
 * Builder for constructing a {@link VendorItemQualityUpdatePolicy} using a provided strategy factory.
 * Implementations encapsulate strategy-specific configuration such as rate schedules and expiration behavior.
 */
public interface VendorItemQualityUpdatePolicyBuilder {

    /**
     * Builds a concrete {@link VendorItemQualityUpdatePolicy} using the given strategy factory.
     *
     * @param factory the strategy factory to use
     * @return the constructed policy instance
     */
    VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory);

    /**
     * Returns a builder for creating a linear quality update policy.
     *
     * @return a new {@link LinearBuilder}
     */
    static LinearBuilder linearPolicy()
    {
        return new LinearBuilder();
    }


    /**
     * Returns a builder for creating a dynamic quality update policy.
     *
     * @return a new {@link DynamicBuilder}
     */
    static DynamicBuilder dynamicPolicy()
    {
        return new DynamicBuilder();
    }


    /**
     * Returns a builder that produces a no-op update policy.
     *
     * @return a builder that always returns the no-op policy
     */
    static VendorItemQualityUpdatePolicyBuilder noUpdate()
    {
        return factory -> NoVendorItemQualityUpdatePolicy.INSTANCE;
    }

    /**
     * Builder for a dynamic quality update policy based on expiration timeline and post-expiration rules.
     */
    final class LinearBuilder implements VendorItemQualityUpdatePolicyBuilder {
        private int initialRate = 0;
        private int expiredRate = 0;
        /**
         * Sets the daily quality change rate to be applied before the item expires.
         *
         * @param dailyRate the quality update rate for non-expired items (can be negative to degrade)
         * @return this builder instance for method chaining
         */
        public LinearBuilder withDailyChangeRate(int dailyRate) {
            this.initialRate = dailyRate;
            return this;
        }

        /**
         * Sets the daily quality change rate to be applied after the item has expired.
         *
         * @param expiredRate the quality update rate for expired items (can be negative to degrade)
         * @return this builder instance for method chaining
         */
        public LinearBuilder withDailyChangeRateAfterExpiration(int expiredRate) {
            this.expiredRate = expiredRate;
            return this;
        }

        @Override
        public VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory)
        {
            assert factory != null;
            LinearQualityUpdateStrategyParameters parameters = new LinearQualityUpdateStrategyParameters(initialRate, expiredRate);
            LinearQualityUpdateStrategy strategy = factory.GetLinearStrategy();
            return new StrategyBasedVendorItemQualityUpdatePolicy<>(strategy, parameters);
        }
    }

    /**
     * Builder for a dynamic quality update policy based on expiration timeline and post-expiration rules.
     */
    final class DynamicBuilder implements VendorItemQualityUpdatePolicyBuilder {

        private int initialRate = 0;
        private final DynamicQualityUpdateRateTimeline timeline = new DynamicQualityUpdateRateTimeline();
        private Integer qualityAfterExpiration = null;

        /**
         * Sets the initial default daily quality change rate to use when no timeline step matches.
         *
         * @param initialRate the fallback quality update rate
         * @return this builder instance for method chaining
         */
        public DynamicBuilder withInitialDailyChangeRate(int initialRate) {
            this.initialRate = initialRate;
            return this;
        }

        /**
         * Defines a timeline-based quality update rate for items with fewer expiration days than the specified threshold.
         * <p>
         * When the item's remaining expiration days are less than {@code expirationDays}, the specified {@code rate} will apply.
         * If multiple steps are defined, the earliest matching step (lowest threshold) is used.
         * </p>
         *
         * @param rate            the quality change rate to apply
         * @param expirationDays  the threshold expiration day (exclusive upper bound)
         * @return this builder instance for method chaining
         */
        public DynamicBuilder withRateBelowExpirationDays(int rate, int expirationDays) {
            timeline.setRateBelowExpirationDays(rate, expirationDays);
            return this;
        }

        /**
         * Specifies a fixed quality value to be applied after the item has expired.
         * <p>
         * If set, this value overrides any rate-based updates once the item is expired.
         * </p>
         *
         * @param quality the static quality value to assign after expiration
         * @return this builder instance for method chaining
         */
        public DynamicBuilder withQualityAfterExpiration(int quality) {
            qualityAfterExpiration = quality;
            return this;
        }

        @Override
        public VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory) {
            assert factory != null;
            DynamicQualityUpdateStrategyParameters parameters = new DynamicQualityUpdateStrategyParameters(initialRate, timeline, qualityAfterExpiration);
            DynamicQualityUpdateStrategy strategy = factory.GetDynamicStrategy();
            return new StrategyBasedVendorItemQualityUpdatePolicy<>(strategy, parameters);
        }
    }
}
