package com.gildedrose.domain.vendor.inventory.quality.policies;

import com.gildedrose.domain.vendor.item.VendorItem;
import com.gildedrose.domain.vendor.item.VendorItemKind;
import com.gildedrose.domain.vendor.item.VendorItemRarity;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.builders.VendorItemQualityUpdatePolicyBuilder;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;

import java.util.HashMap;
import java.util.Optional;

public class VendorInventoryDailyQualityUpdatePolicy {
    private final VendorItemQualityUpdatePolicy defaultPolicy;
    private final HashMap<VendorItemKind, VendorItemQualityUpdatePolicy> itemKindPolicy;
    private final HashMap<VendorItemRarity, VendorItemQualityUpdatePolicy> itemRarityPolicies;

    private VendorInventoryDailyQualityUpdatePolicy(Builder builder) {
        this.defaultPolicy = builder.defaultPolicy;
        this.itemKindPolicy = builder.itemKindPolicies;
        this.itemRarityPolicies = builder.itemRarityPolicies;
    }

    public void apply(VendorItem item)
    {
        getPolicy(item).ifPresent(item::updateDaily);
    }

    private Optional<VendorItemQualityUpdatePolicy> getPolicy(VendorItem item)
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

    public static Builder builder(VendorItemQualityUpdateStrategyFactory strategyFactory) {
        return new Builder(strategyFactory);
    }

    public static class Builder {
        private final VendorItemQualityUpdateStrategyFactory strategyFactory;
        private final HashMap<VendorItemKind, VendorItemQualityUpdatePolicy> itemKindPolicies;
        private final HashMap<VendorItemRarity, VendorItemQualityUpdatePolicy> itemRarityPolicies;
        private VendorItemQualityUpdatePolicy defaultPolicy;

        public Builder(VendorItemQualityUpdateStrategyFactory strategyFactory) {
            itemKindPolicies = new HashMap<>();
            itemRarityPolicies = new HashMap<>();
            defaultPolicy = null;
            this.strategyFactory = strategyFactory;
        }

        public Builder withPolicy(VendorItemKind itemKind, VendorItemQualityUpdatePolicyBuilder builder) {
            itemKindPolicies.put(itemKind, builder.build(strategyFactory));
            return this;
        }

        public Builder withPolicy(VendorItemRarity rarity, VendorItemQualityUpdatePolicyBuilder builder) {
            itemRarityPolicies.put(rarity, builder.build(strategyFactory));
            return this;
        }

        public Builder withDefaultPolicy(VendorItemQualityUpdatePolicyBuilder builder) {
            defaultPolicy = builder.build(strategyFactory);
            return this;
        }

        public VendorInventoryDailyQualityUpdatePolicy build() {
            return new VendorInventoryDailyQualityUpdatePolicy(this);
        }
    }
}
