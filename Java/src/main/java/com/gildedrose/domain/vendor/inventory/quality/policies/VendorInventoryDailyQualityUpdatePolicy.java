package com.gildedrose.domain.vendor.inventory.quality.policies;

import com.gildedrose.domain.vendor.item.VendorItem;
import com.gildedrose.domain.vendor.item.VendorItemKind;
import com.gildedrose.domain.vendor.item.VendorItemRarity;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;

import java.util.HashMap;
import java.util.Optional;

public class VendorInventoryDailyQualityUpdatePolicy {
    private final VendorItemQualityUpdatePolicy defaultPolicy;
    private final HashMap<VendorItemKind, VendorItemQualityUpdatePolicy> itemKindPolicy;
    private final HashMap<VendorItemRarity, VendorItemQualityUpdatePolicy> itemRarityPolicies;

    public VendorInventoryDailyQualityUpdatePolicy(VendorItemQualityUpdatePolicy defaultPolicy, HashMap<VendorItemKind, VendorItemQualityUpdatePolicy> policies, HashMap<VendorItemRarity, VendorItemQualityUpdatePolicy> itemRarityPolicies) {
        this.defaultPolicy = defaultPolicy;
        this.itemKindPolicy = policies;
        this.itemRarityPolicies = itemRarityPolicies;
    }

    public Optional<VendorItemQualityUpdatePolicy> forItem(VendorItem item)
    {
        if(itemRarityPolicies.containsKey(item.getRarity()))
        {
            return Optional.of(itemRarityPolicies.get(item.getRarity()));
        }
        if(itemKindPolicy.containsKey(item.getKind()))
        {
            return Optional.of(itemKindPolicy.get(item.getKind()));
        }
        return Optional.ofNullable(defaultPolicy);
    }
}
