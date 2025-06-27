package com.gildedrose.domain.vendor.inventory.quality.policies.builders;

import com.gildedrose.domain.vendor.inventory.quality.policies.VendorInventoryDailyQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.VendorItemKind;
import com.gildedrose.domain.vendor.item.VendorItemRarity;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.builders.VendorItemQualityUpdatePolicyBuilder;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;

import java.util.HashMap;
import java.util.Map;

public class VendorInventoryDailyQualityUpdatePolicyBuilder {
    private final VendorItemQualityUpdateStrategyFactory strategyFactory;
    private final HashMap<VendorItemKind, VendorItemQualityUpdatePolicyBuilder> kindPolicies;
    private final HashMap<VendorItemRarity, VendorItemQualityUpdatePolicyBuilder> rarityPolicies;
    private VendorItemQualityUpdatePolicyBuilder defaultPolicyBuilder;

    public VendorInventoryDailyQualityUpdatePolicyBuilder(VendorItemQualityUpdateStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
        kindPolicies = new HashMap<>();
        rarityPolicies = new HashMap<>();
        defaultPolicyBuilder = null;
    }

    public VendorInventoryDailyQualityUpdatePolicyBuilder withPolicy(VendorItemKind itemKind, VendorItemQualityUpdatePolicyBuilder builder) {
        kindPolicies.put(itemKind, builder);
        return this;
    }

    public VendorInventoryDailyQualityUpdatePolicyBuilder withPolicy(VendorItemRarity rarity, VendorItemQualityUpdatePolicyBuilder builder) {
        rarityPolicies.put(rarity, builder);
        return this;
    }

    public VendorInventoryDailyQualityUpdatePolicyBuilder withDefaultPolicy(VendorItemQualityUpdatePolicyBuilder builder) {
        defaultPolicyBuilder = builder;
        return this;
    }

    public VendorInventoryDailyQualityUpdatePolicy build() {
        VendorItemQualityUpdatePolicy defaultPolicy = null;
        if(defaultPolicyBuilder != null) {
            defaultPolicy = defaultPolicyBuilder.build(strategyFactory);
        }

        HashMap<VendorItemKind, VendorItemQualityUpdatePolicy> builtKindPolicies = buildPolicies(kindPolicies);
        HashMap<VendorItemRarity, VendorItemQualityUpdatePolicy> buildRarityPolicies = buildPolicies(rarityPolicies);
        return new VendorInventoryDailyQualityUpdatePolicy(defaultPolicy, builtKindPolicies, buildRarityPolicies);
    }

    private <Key>HashMap<Key, VendorItemQualityUpdatePolicy> buildPolicies(HashMap<Key, VendorItemQualityUpdatePolicyBuilder> builders)
    {
        HashMap<Key, VendorItemQualityUpdatePolicy> builtPolicies= new HashMap<>();
        for (Map.Entry<Key, VendorItemQualityUpdatePolicyBuilder> entry : builders.entrySet()) {
            VendorItemQualityUpdatePolicy policy = entry
                .getValue()
                .build(strategyFactory);
            builtPolicies.put(entry.getKey(), policy);
        }
        return builtPolicies;
    }
}
