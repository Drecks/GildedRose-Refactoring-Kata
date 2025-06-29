package com.gildedrose.domain.vendor.item.quality.policies.builders;

import com.gildedrose.domain.vendor.item.quality.policies.StrategyBasedVendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.NoVendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.DynamicQualityUpdateRateTimeline;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.DynamicQualityUpdateStrategyParameters;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.LinearQualityUpdateStrategyParameters;
import com.gildedrose.domain.vendor.item.quality.policies.strategies.VendorItemQualityUpdateStrategy;

public interface VendorItemQualityUpdatePolicyBuilder {

    VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory);

    static Linear linearPolicy()
    {
        return new Linear();
    }

    static Dynamic dynamicPolicy()
    {
        return new Dynamic();
    }

    static VendorItemQualityUpdatePolicyBuilder noUpdate()
    {
        return factory -> NoVendorItemQualityUpdatePolicy.INSTANCE;
    }

    class Linear implements VendorItemQualityUpdatePolicyBuilder {
        private int initialRate = 0;
        private int expiredRate = 0;
        public Linear withDailyChangeRate(int dailyRate) {
            this.initialRate = dailyRate;
            return this;
        }
        public Linear withDailyChangeRateAfterExpiration(int expiredRate) {
            this.expiredRate = expiredRate;
            return this;
        }
        public VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory)
        {
            LinearQualityUpdateStrategyParameters parameters = new LinearQualityUpdateStrategyParameters(initialRate, expiredRate);
            VendorItemQualityUpdateStrategy<LinearQualityUpdateStrategyParameters> strategy = factory.GetLinearStrategy();
            return new StrategyBasedVendorItemQualityUpdatePolicy<>(strategy, parameters);
        }
    }

    class Dynamic implements VendorItemQualityUpdatePolicyBuilder {

        private int initialRate = 0;
        private final DynamicQualityUpdateRateTimeline steps = new DynamicQualityUpdateRateTimeline();
        private Integer qualityAfterExpiration = null;

        public Dynamic withInitialDailyChangeRate(int initialRate) {
            this.initialRate = initialRate;
            return this;
        }

        public Dynamic withRateBelowExpirationDays(int rate, int expirationDays) {
            steps.setRateBelowExpirationDays(rate, expirationDays);
            return this;
        }

        public Dynamic withQualityAfterExpiration(int quality) {
            qualityAfterExpiration = quality;
            return this;
        }

        public VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory) {
            DynamicQualityUpdateStrategyParameters parameters = new DynamicQualityUpdateStrategyParameters(initialRate, steps, qualityAfterExpiration);
            VendorItemQualityUpdateStrategy<DynamicQualityUpdateStrategyParameters> strategy = factory.GetDynamicStrategy();
            return new StrategyBasedVendorItemQualityUpdatePolicy<>(strategy, parameters);
        }
    }
}
