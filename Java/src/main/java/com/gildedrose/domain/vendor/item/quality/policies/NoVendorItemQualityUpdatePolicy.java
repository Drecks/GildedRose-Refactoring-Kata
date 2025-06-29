package com.gildedrose.domain.vendor.item.quality.policies;

import com.gildedrose.domain.vendor.item.VendorItem;

/**
 * A {@link VendorItemQualityUpdatePolicy} implementation that performs no update.
 * <p>
 * This policy is used for items whose quality should remain unchanged,
 * such as legendary or static items.
 * </p>
 */
public final class NoVendorItemQualityUpdatePolicy implements VendorItemQualityUpdatePolicy {

    /** A shared singleton instance. */
    public static final NoVendorItemQualityUpdatePolicy INSTANCE = new NoVendorItemQualityUpdatePolicy();

    private NoVendorItemQualityUpdatePolicy() {}

    @Override
    public void apply(VendorItem item) {
        // no-op
    }

    @Override
    public String toString() {
        return "NoVendorItemQualityUpdatePolicy";
    }
}
