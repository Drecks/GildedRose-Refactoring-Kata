package com.gildedrose.domain.vendor.inventory;

import com.gildedrose.domain.vendor.inventory.quality.policies.VendorInventoryQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.VendorItem;

import java.util.*;


/**
 * Represents a vendor's inventory as an immutable collection of {@link VendorItem}s.
 * <p>
 * This class models the behavior of a vendor's stock where, once created, the set of items
 * cannot be modified. Each day, the quality of each item may degrade, improve, or remain the same,
 * depending on the provided {@link VendorInventoryQualityUpdatePolicy}.
 * </p>
 *
 * <p>
 * Usage example:
 * </p>
 * <pre>{@code
 * VendorInventory inventory = new VendorInventory(itemList);
 * inventory.updateDaily(updatePolicy); // Applies quality updates for one day
 * }</pre>
 *
 * @see VendorItem
 * @see VendorInventoryQualityUpdatePolicy
 */
public class VendorInventory
{
    private final List<VendorItem> items;
    public VendorInventory(Collection<VendorItem> items) {
        assert items != null;
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }


    /**
     * Applies the specified daily quality update policy to each item in the inventory.
     * <p>
     * This simulates one "day" passing in the system, during which item quality
     * may change according to the policy logic.
     * </p>
     *
     * @param updatePolicy the policy to apply to each item; must not be null
     */
    public void updateDaily(VendorInventoryQualityUpdatePolicy updatePolicy)
    {
        assert updatePolicy != null;
        items.forEach(updatePolicy::apply);
    }
}
