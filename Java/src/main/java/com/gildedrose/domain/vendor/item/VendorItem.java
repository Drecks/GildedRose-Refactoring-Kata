package com.gildedrose.domain.vendor.item;

import com.gildedrose.Item;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;

public class VendorItem {
    private final Item item;
    private final VendorItemRarity rarity;
    private final VendorItemKind kind;
    private final int minQuality;
    private final int maxQuality;

    public VendorItem(Item item, VendorItemRarity rarity, VendorItemKind kind, int minQuality, int maxQuality) {
        this.item = item;
        this.rarity = rarity;
        this.kind = kind;
        this.minQuality = minQuality;
        this.maxQuality = maxQuality;
    }

    public int getQuality() {
        return item.quality;
    }

    public boolean isExpired() {
        return item.sellIn < 0;
    }

    public VendorItemRarity getRarity() {
       return rarity;
    }

    public VendorItemKind getKind() {
        return kind;
    }

    public int getExpirationDays() {
        return item.sellIn;
    }

    public void updateDaily(VendorItemQualityUpdatePolicy updatePolicy)
    {
        if(rarity != VendorItemRarity.Legendary) {
            item.sellIn -= 1;
        }
        updatePolicy.apply(this);
    }

    public void setQuality(int quality) {
        this.item.quality = Math.min(maxQuality, Math.max(minQuality, quality));
    }
}

