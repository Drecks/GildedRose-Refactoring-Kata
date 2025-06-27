package com.gildedrose.domain.vendor.item.quality.policies;

import com.gildedrose.domain.vendor.item.VendorItem;

public interface VendorItemQualityUpdatePolicy {
    void apply(VendorItem item);
}
