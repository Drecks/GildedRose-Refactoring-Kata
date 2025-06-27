package com.gildedrose;

import com.gildedrose.domain.vendor.inventory.VendorInventory;
import com.gildedrose.domain.vendor.inventory.quality.policies.VendorInventoryDailyQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.VendorItem;
import com.gildedrose.domain.vendor.item.VendorItemKind;
import com.gildedrose.domain.vendor.item.VendorItemRarity;
import com.gildedrose.domain.vendor.item.quality.policies.builders.VendorItemQualityUpdatePolicyBuilder;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

class GildedRose {
    VendorInventory inventory;
    VendorInventoryDailyQualityUpdatePolicy qualityUpdatePolicy;

    public GildedRose(Item[] items) {
        inventory = new VendorInventory(Arrays.stream(items).map(GildedRose::mapToVendorItem).collect(Collectors.toList()));
        VendorItemQualityUpdatePolicyBuilder defaultPolicy = VendorItemQualityUpdatePolicyBuilder
            .linearPolicy()
            .withDailyChangeRate(-1)
            .withDailyChangeRateAfterExpiration(-2);
        VendorItemQualityUpdatePolicyBuilder briePolicy = VendorItemQualityUpdatePolicyBuilder
            .linearPolicy()
            .withDailyChangeRate(1)
            .withDailyChangeRateAfterExpiration(2);
        VendorItemQualityUpdatePolicyBuilder backstagePolicy = VendorItemQualityUpdatePolicyBuilder
            .dynamicPolicy()
            .withInitialDailyChangeRate(1)
            .withRateBelowExpirationDays(2, 10)
            .withRateBelowExpirationDays(3, 5)
            .withQualityAfterExpiration(0);
        VendorItemQualityUpdateStrategyFactory factory = new VendorItemQualityUpdateStrategyFactory();

        qualityUpdatePolicy = new VendorInventoryDailyQualityUpdatePolicy.Builder(factory)
            .withDefaultPolicy(defaultPolicy)
            .withPolicy(VendorItemRarity.Legendary, VendorItemQualityUpdatePolicyBuilder.noUpdate())
            .withPolicy(VendorItemKind.AgedBrie, briePolicy)
            .withPolicy(VendorItemKind.BackstagePass, backstagePolicy)
            .build();
    }

    public void updateQuality() {
        inventory.updateDaily(qualityUpdatePolicy);
    }

    private static VendorItem mapToVendorItem(Item item) {
        VendorItemRarity rarity = mapItemToRarity(item);
        VendorItemKind kind = mapItemToKind(item);
        if (rarity == VendorItemRarity.Legendary) {
            return new VendorItem(item, rarity, kind, ItemConstants.LEGENDARY_QUALITY, ItemConstants.LEGENDARY_QUALITY);
        } else {
            return new VendorItem(item, rarity, kind, ItemConstants.MIN_NORMAL_QUALITY, ItemConstants.MAX_NORMAL_QUALITY);
        }
    }

    private static VendorItemRarity mapItemToRarity(Item item) {
        if (item.name.equalsIgnoreCase(ItemConstants.SULFURAS)) {
            return VendorItemRarity.Legendary;
        }
        return VendorItemRarity.Normal;
    }

    private static VendorItemKind mapItemToKind(Item item) {
        switch (item.name) {
            case ItemConstants.SULFURAS:
                return VendorItemKind.Sulfuras;
            case ItemConstants.AGED_BRIE:
                return VendorItemKind.AgedBrie;
            case ItemConstants.BACKSTAGE_PASS:
                return VendorItemKind.BackstagePass;
            default:
                return VendorItemKind.Default;
        }
    }
}
