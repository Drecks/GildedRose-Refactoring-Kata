package com.gildedrose.domain.vendor.item;


import com.gildedrose.Item;
import com.gildedrose.domain.vendor.item.quality.policies.VendorItemQualityUpdatePolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VendorItemTest {

    private VendorItem createItem(int sellIn, int quality, int minQuality, int maxQuality, VendorItemRarity rarity) {
        return new VendorItem(
            new Item("Test Item", sellIn, quality),
            rarity,
            VendorItemKind.Default,
            minQuality,
            maxQuality
        );
    }
    @Test
    void updateDaily_when_non_legendary_item_then_applies_policy_and_decrements_sellIn() {
        // Arrange
        VendorItem systemUnderTest = createItem(10, 20, 0, 50, VendorItemRarity.Common);
        VendorItemQualityUpdatePolicy mockPolicy = mock(VendorItemQualityUpdatePolicy.class);

        // Act
        systemUnderTest.updateDaily(mockPolicy);

        // Assert
        assertEquals(9, systemUnderTest.getExpirationDays());
        verify(mockPolicy).apply(systemUnderTest);
    }

    @Test
    void updateDaily_when_legendary_item_then_do_not_decrement_sellIn() {
        // Arrange
        VendorItem systemUnderTest = createItem(5, 80, 80, 80, VendorItemRarity.Legendary);
        VendorItemQualityUpdatePolicy mockPolicy = mock(VendorItemQualityUpdatePolicy.class);

        // Act
        systemUnderTest.updateDaily(mockPolicy);

        // Assert
        assertEquals(5, systemUnderTest.getExpirationDays()); // unchanged
        verify(mockPolicy).apply(systemUnderTest);           // still applies
    }

    @Test
    void setQuality_when_quality_is_within_bounds_sets_value_as_is() {
        //ARRANGE
        VendorItem item = createItem(5, 10, 0, 50,  VendorItemRarity.Common);

        //ACT
        item.setQuality(25);

        //ASSERT
        assertEquals(25, item.getQuality());
    }

    @Test
    void setQuality_when_quality_is_below_min_clamps_to_min() {
        //ARRANGE
        VendorItem item = createItem(5, 10, 0, 50, VendorItemRarity.Common);

        //ACT
        item.setQuality(-5);

        //ASSERT
        assertEquals(0, item.getQuality());
    }

    @Test
    void setQuality_when_quality_is_above_max_clamps_to_max() {
        //ARRANGE
        VendorItem item = createItem(5, 10, 0, 50, VendorItemRarity.Common);

        //ACT
        item.setQuality(100);

        //ASSERT
        assertEquals(50, item.getQuality());
    }

}
