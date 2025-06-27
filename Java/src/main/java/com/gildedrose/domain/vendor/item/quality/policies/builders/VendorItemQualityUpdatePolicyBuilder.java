package com.gildedrose.domain.vendor.item.quality.policies.builders;

import com.gildedrose.domain.vendor.item.quality.policies.VendorItemNoQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;

public interface VendorItemQualityUpdatePolicyBuilder {
    VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory);

    static LinearVendorItemQualityUpdatePolicyBuilder linearPolicy()
    {
        return new LinearVendorItemQualityUpdatePolicyBuilder();
    }

    static DynamicVendorItemQualityUpdatePolicyBuilder dynamicPolicy()
    {
        return new DynamicVendorItemQualityUpdatePolicyBuilder();
    }

    static VendorItemQualityUpdatePolicyBuilder noUpdate()
    {
        return new VendorItemQualityUpdatePolicyBuilder() {
            @Override
            public VendorItemQualityUpdatePolicy build(VendorItemQualityUpdateStrategyFactory factory) {
                return new VendorItemNoQualityUpdatePolicy();
            }
        };
    }
}
