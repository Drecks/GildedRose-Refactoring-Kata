package com.gildedrose.domain.vendor.item.quality.policies;

import com.gildedrose.domain.vendor.item.VendorItem;

public class VendorItemNoQualityUpdatePolicy implements VendorItemQualityUpdatePolicy {
    @Override
    public void apply(VendorItem item) {
        return;
    }
}
