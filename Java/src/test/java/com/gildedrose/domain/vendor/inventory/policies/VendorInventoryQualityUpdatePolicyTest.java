package com.gildedrose.domain.vendor.inventory.policies;

import com.gildedrose.Item;
import com.gildedrose.domain.vendor.inventory.quality.policies.VendorInventoryQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.VendorItem;
import com.gildedrose.domain.vendor.item.VendorItemKind;
import com.gildedrose.domain.vendor.item.VendorItemRarity;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.quality.policies.factories.VendorItemQualityUpdateStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class VendorInventoryQualityUpdatePolicyTest {

    private VendorItem item;
    private VendorItemQualityUpdatePolicy defaultPolicy;
    private VendorItemQualityUpdatePolicy rarityPolicy;
    private VendorItemQualityUpdatePolicy kindPolicy;

    @BeforeEach
    void setUp() {
        Item rawItem = new Item("Test Item", 5, 10);
        item = spy(new VendorItem(rawItem, VendorItemRarity.Common, VendorItemKind.Default, 0, 50));

        defaultPolicy = mock(VendorItemQualityUpdatePolicy.class);
        rarityPolicy = mock(VendorItemQualityUpdatePolicy.class);
        kindPolicy = mock(VendorItemQualityUpdatePolicy.class);
    }

    @Test
    void apply_when_rarity_policy_available_applies_rarity_policy() {
        //ARRANGE
        VendorInventoryQualityUpdatePolicy systemUnderTest =
            VendorInventoryQualityUpdatePolicy.builder(new VendorItemQualityUpdateStrategyFactory())
                    .withPolicy(VendorItemRarity.Common, f -> rarityPolicy)
                    .withPolicy(VendorItemKind.Default, f -> kindPolicy)
                        .build();

        //ACT
        systemUnderTest.apply(item);

        //ASSERT
        verify(item).updateDaily(rarityPolicy);
        verify(item, never()).updateDaily(defaultPolicy);
        verify(item, never()).updateDaily(kindPolicy);
    }

    @Test
    void apply_when_no_rarity_policy_but_kind_policy_available_then_apply_kind_policy() {
        //ARRANGE
        VendorInventoryQualityUpdatePolicy systemUnderTest =
            VendorInventoryQualityUpdatePolicy.builder(new VendorItemQualityUpdateStrategyFactory())
                .withPolicy(VendorItemKind.Default, f -> kindPolicy)
                .withDefaultPolicy(f -> defaultPolicy)
                .build();

        //ACT
        systemUnderTest.apply(item);

        //ASSERT
        verify(item).updateDaily(kindPolicy);
        verify(item, never()).updateDaily(defaultPolicy);
    }

    @Test
    void apply_when_no_policy_available_then_apply_default_policy() {
        //ARRANGE
        VendorInventoryQualityUpdatePolicy systemUnderTest =
            VendorInventoryQualityUpdatePolicy.builder(new VendorItemQualityUpdateStrategyFactory())
                .withDefaultPolicy(f -> defaultPolicy)
                .build();

        //ACT
        systemUnderTest.apply(item);

        //ASSERT
        verify(item).updateDaily(defaultPolicy);
    }


    @Test
    void apply_when_no_policy_then_does_not_update_item() {
        //ARRANGE
        VendorInventoryQualityUpdatePolicy systemUnderTest =
            VendorInventoryQualityUpdatePolicy.builder(new VendorItemQualityUpdateStrategyFactory())
                .build();

        //ACT
        systemUnderTest.apply(item);

        //ASSERT
        verify(item, never()).updateDaily(any());

    }
}
