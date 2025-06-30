package com.gildedrose.domain.vendor.inventory.quality.policies;

import com.gildedrose.domain.vendor.item.VendorItem;
import com.gildedrose.domain.vendor.item.VendorItemKind;
import com.gildedrose.domain.vendor.item.VendorItemRarity;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.builders.VendorItemQualityUpdatePolicyBuilder;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Defines the quality update policy for vendor inventory items
 * <p>
 * This class supports assigning custom {@link VendorItemQualityUpdatePolicy} instances
 * based on either the item's kind ({@link VendorItemKind}) or rarity ({@link VendorItemRarity}).
 * If no specific policy is defined for a given item, a default policy is applied.
 * </p>
 *
 * <p>
 * Use the {@link PolicyBuilder} to construct an instance of this class, optionally specifying
 * item kind and rarity-based policies as well as the default policy.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>{@code
 * VendorInventoryDailyQualityUpdatePolicy policy = VendorInventoryDailyQualityUpdatePolicy
 *     .builder(strategyFactory)
 *     .withPolicy(VendorItemKind.BackstagePass, bpPolicyBuilder)
 *     .withPolicy(VendorItemRarity.Legendary, legendaryPolicyBuilder)
 *     .withDefaultPolicy(defaultPolicyBuilder)
 *     .build();
 *
 * policy.apply(item); // Applies the appropriate daily update policy to the item
 * }</pre>
 *
 * @see VendorItemQualityUpdatePolicy
 * @see VendorItemQualityUpdatePolicyBuilder
 * @see VendorItemQualityUpdateStrategyFactory
 */
public final class VendorInventoryQualityUpdatePolicy {
    private final VendorItemQualityUpdatePolicy defaultPolicy;
    private final Map<VendorItemKind, VendorItemQualityUpdatePolicy> itemKindPolicies;
    private final Map<VendorItemRarity, VendorItemQualityUpdatePolicy> itemRarityPolicies;

    /**
     * Applies the appropriate quality update policy to the given {@link VendorItem}.
     * <p>
     * The method determines the most specific applicable policy in the following order:
     * </p>
     * <ol>
     *     <li>Policy based on item rarity ({@link VendorItemRarity})</li>
     *     <li>Policy based on item kind ({@link VendorItemKind})</li>
     *     <li>Default policy (if no specific match is found)</li> * </ol>
     *
     * @param item the vendor item to which the quality update should be applied; must not be null
     */
    public void apply(VendorItem item)
    {
        assert item != null;
        getPolicy(item).ifPresent(item::updateDaily);
    }

    private VendorInventoryQualityUpdatePolicy(PolicyBuilder policyBuilder) {
        this.defaultPolicy = policyBuilder.defaultPolicy;
        this.itemKindPolicies = policyBuilder.itemKindPolicies;
        this.itemRarityPolicies = policyBuilder.itemRarityPolicies;
    }

    private Optional<VendorItemQualityUpdatePolicy> getPolicy(VendorItem item) {
        VendorItemQualityUpdatePolicy policy = itemRarityPolicies.getOrDefault(
            item.getRarity(),
            itemKindPolicies.getOrDefault(item.getKind(), defaultPolicy)
        );
        return Optional.ofNullable(policy);
    }

    /**
     * Creates a new {@link PolicyBuilder} instance to construct a {@code VendorInventoryDailyQualityUpdatePolicy}.
     * <p>
     * The builder uses the provided {@link VendorItemQualityUpdateStrategyFactory}
     * to instantiate the actual policy implementations.
     * </p>
     *
     * @param strategyFactory the strategy factory to be used during policy construction; must not be null
     * @return a new {@code Builder} instance
     */
    public static PolicyBuilder builder(VendorItemQualityUpdateStrategyFactory strategyFactory) {
        assert strategyFactory != null;
        return new PolicyBuilder(strategyFactory);
    }

    /**
     * Builder for constructing instances of {@link VendorInventoryQualityUpdatePolicy}.
     * <p>
     * This builder allows you to define custom quality update policies for specific item kinds
     * ({@link VendorItemKind}) and rarities ({@link VendorItemRarity}), as well as a fallback default policy.
     * The builder uses a shared {@link VendorItemQualityUpdateStrategyFactory} to construct policy instances
     * from policy builders.
     * </p>
     *
     * @see VendorInventoryQualityUpdatePolicy
     * @see VendorItemQualityUpdatePolicy
     * @see VendorItemQualityUpdatePolicyBuilder
     * @see VendorItemQualityUpdateStrategyFactory
     */
    public static class PolicyBuilder {
        private final VendorItemQualityUpdateStrategyFactory strategyFactory;
        private final HashMap<VendorItemKind, VendorItemQualityUpdatePolicy> itemKindPolicies;
        private final HashMap<VendorItemRarity, VendorItemQualityUpdatePolicy> itemRarityPolicies;
        private VendorItemQualityUpdatePolicy defaultPolicy;

        public PolicyBuilder(VendorItemQualityUpdateStrategyFactory strategyFactory) {
            itemKindPolicies = new HashMap<>();
            itemRarityPolicies = new HashMap<>();
            defaultPolicy = null;
            this.strategyFactory = strategyFactory;
        }

        /**
         * Sets a quality update policy specific to the given item kind.
         * <p>
         * When the policy is later applied, this policy takes precedence over the default policy,
         * but is overridden by any matching rarity policy.
         * </p>
         *
         * @param itemKind the kind of item to associate with the given policy; must not be null
         * @param builder the policy builder used to construct the quality update policy; must not be null
         * @return this builder instance for fluent chaining
         */
        public PolicyBuilder withPolicy(VendorItemKind itemKind, VendorItemQualityUpdatePolicyBuilder builder) {
            assert itemKind != null && builder != null;
            itemKindPolicies.put(itemKind, builder.build(strategyFactory));
            return this;
        }


        /**
         * Sets a quality update policy specific to the given item rarity.
         * <p>
         * When the policy is later applied, this policy takes precedence over both kind-specific
         * and default policies.
         * </p>
         *
         * @param rarity the rarity of item to associate with the given policy; must not be null
         * @param builder the policy builder used to construct the quality update policy; must not be null
         * @return this builder instance for fluent chaining
         */
        public PolicyBuilder withPolicy(VendorItemRarity rarity, VendorItemQualityUpdatePolicyBuilder builder) {
            assert rarity != null && builder != null;
            itemRarityPolicies.put(rarity, builder.build(strategyFactory));
            return this;
        }


        /**
         * Sets the default policy to use when no kind- or rarity-specific policy applies.
         *
         * @param builder the policy builder used to construct the default quality update policy; must not be null
         * @return this builder instance for fluent chaining
         */
        public PolicyBuilder withDefaultPolicy(VendorItemQualityUpdatePolicyBuilder builder) {
            assert builder != null;
            defaultPolicy = builder.build(strategyFactory);
            return this;
        }


        /**
         * Builds the {@link VendorInventoryQualityUpdatePolicy} instance using the configured policies.
         *
         * @return a new immutable {@code VendorInventoryDailyQualityUpdatePolicy} instance
         */
        public VendorInventoryQualityUpdatePolicy build() {
            return new VendorInventoryQualityUpdatePolicy(this);
        }
    }
}
