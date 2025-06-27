package com.gildedrose.domain.vendor.inventory;

import com.gildedrose.domain.vendor.inventory.quality.policies.VendorInventoryDailyQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.VendorItem;

import java.util.*;

/**
 * A vendor's inventory is an immutable collection of VendorItems
 * Each day every item's quality either degrades, improve or stay the same
 */
public class VendorInventory
{
    private final List<VendorItem> items;
    public VendorInventory(Collection<VendorItem> items) {
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }
    public void updateDaily(VendorInventoryDailyQualityUpdatePolicy updatePolicy)
    {
        items.forEach(updatePolicy::apply);
    }
}
