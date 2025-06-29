package com.gildedrose.domain.vendor.item.quality.policies;

import com.gildedrose.domain.vendor.item.VendorItem;

/**
 * Defines a policy for updating the quality of a {@link VendorItem} as part of a vendor's inventory management.
 * <p>
 * Implementations of this interface encapsulate the logic for adjusting an item's quality on a daily basis,
 * typically using a specific strategy or rule, such as degrading quality, improving it, or keeping it constant.
 * </p>
 *
 * <p>
 * The policy is applied via the {@link #apply(VendorItem)} method, which performs any necessary updates
 * to the item's state — including enforcing quality bounds where appropriate.
 * </p>
 *
 * <p>
 * Example implementations include:
 * <ul>
 *   <li>{@code StrategyBasedVendorItemQualityUpdatePolicy}, which delegates to a reusable strategy and parameters</li>
 *   <li>Custom policies for legendary, expiring, or event-based items</li>
 * </ul>
 * </p>
 *
 * @see VendorItem
 * @see StrategyBasedVendorItemQualityUpdatePolicy
 */
public interface VendorItemQualityUpdatePolicy {
    /**
     * Applies this policy to the given vendor item, updating its quality as needed.
     * <p>
     * This method is typically called once per day during the inventory update cycle.
     * </p>
     *
     * @param item the vendor item to update; must not be null
     */
    void apply(VendorItem item);
}
