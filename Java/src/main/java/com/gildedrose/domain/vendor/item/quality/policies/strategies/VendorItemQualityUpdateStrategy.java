package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import com.gildedrose.domain.vendor.item.VendorItem;

public interface VendorItemQualityUpdateStrategy<TParameters> {
    public void update (VendorItem item, TParameters params);
}
