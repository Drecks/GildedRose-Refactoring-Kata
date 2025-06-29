package com.gildedrose.domain.vendor.item;

import com.gildedrose.Item;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;

/**
 * Represents an item in a vendor's inventory, encapsulating its rarity, kind,
 * and quality constraints.
 * <p>
 * A {@code VendorItem} wraps a mutable {@link Item} instance and provides
 * domain-specific behavior for quality management and daily updates.
 * The quality is bounded between {@code minQuality} and {@code maxQuality}, and
 * the item's expiration (sell-in value) is automatically decreased daily unless
 * the item's quality is updated daily based on a provided quality policy,
 * the item is considered {@code Legendary}.
 * </p>
 *
 * @see VendorItemRarity
 * @see VendorItemKind
 * @see VendorItemQualityUpdatePolicy
 * @see Item
 */
public class VendorItem {
    private final Item item;
    private final VendorItemRarity rarity;
    private final VendorItemKind kind;
    private final int minQuality;
    private final int maxQuality;

    /**
     * Constructs a new {@code VendorItem} with the specified attributes and quality bounds.
     *
     * @param item        the underlying mutable item instance; must not be null
     * @param rarity      the rarity of the item (e.g., Common, Rare, Legendary); must not be null
     * @param kind        the kind/category of the item (e.g., Weapon, Potion); must not be null
     * @param minQuality  the minimum allowed quality value (inclusive)
     * @param maxQuality  the maximum allowed quality value (inclusive); must be greater than minQuality
     */
    public VendorItem(Item item, VendorItemRarity rarity, VendorItemKind kind, int minQuality, int maxQuality) {
        assert item != null;
        assert rarity != null;
        assert kind != null;
        assert minQuality < maxQuality;
        this.item = item;
        this.rarity = rarity;
        this.kind = kind;
        this.minQuality = minQuality;
        this.maxQuality = maxQuality;
    }

    /**
     * @return the item's current quality value
     */
    public int getQuality() {
        return item.quality;
    }


    /**
     * Checks whether the item is expired.
     *
     * @return {@code true} if the item's sell-in value is less than 0; {@code false} otherwise
     */
    public boolean isExpired() {
        return item.sellIn < 0;
    }


    /**
     * Returns the rarity classification of the item.
     *
     * @return the item's rarity
     */
    public VendorItemRarity getRarity() {
       return rarity;
    }

    /**
     * Returns the kind of the item.
     *
     * @return the item's kind
     */
    public VendorItemKind getKind() {
        return kind;
    }


    /**
     * Returns the number of days remaining before the item expires.
     *
     * @return the current sell-in value of the item
     */
    public int getExpirationDays() {
        return item.sellIn;
    }

    /**
     * Applies the daily update logic to the item using the given policy.
     * <p>
     * If the item is not {@code Legendary}, its expiration (sell-in) value is
     * decreased by one. Then, the provided {@link VendorItemQualityUpdatePolicy}
     * is applied to update the item's quality.
     * </p>
     *
     * @param updatePolicy the policy to apply for updating the item's quality; must not be null
     */
    public void updateDaily(VendorItemQualityUpdatePolicy updatePolicy)
    {
        assert updatePolicy != null;
        if(rarity != VendorItemRarity.Legendary) {
            item.sellIn -= 1;
        }
        updatePolicy.apply(this);
    }

    /**
     * Sets the item's quality to a specific value, enforcing the configured quality bounds.
     * <p>
     * If the given value is lower than {@code minQuality}, it is clamped up.
     * If it is higher than {@code maxQuality}, it is clamped down.
     * </p>
     *
     * @param quality the new quality value to assign
     */
    public void setQuality(int quality) {
        this.item.quality = Math.min(maxQuality, Math.max(minQuality, quality));
    }
}

