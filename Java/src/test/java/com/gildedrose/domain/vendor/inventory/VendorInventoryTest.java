package com.gildedrose.domain.vendor.inventory;


import com.gildedrose.domain.vendor.inventory.quality.policies.VendorInventoryQualityUpdatePolicy;
import com.gildedrose.domain.vendor.item.VendorItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class VendorInventoryTest {

    @Test
    void updateDaily_then_applies_policy_to_all_items() {
        // Arrange
        VendorItem item1 = mock(VendorItem.class);
        VendorItem item2 = mock(VendorItem.class);
        VendorInventoryQualityUpdatePolicy mockPolicy = mock(VendorInventoryQualityUpdatePolicy.class);
        VendorInventory systemUnderTest = new VendorInventory(Arrays.asList(item1, item2));

        // Act
        systemUnderTest.updateDaily(mockPolicy);

        // Assert
        verify(mockPolicy).apply(item1);
        verify(mockPolicy).apply(item2);
        verifyNoMoreInteractions(mockPolicy);
    }

    @Test
    void updateDaily_when_inventory_empty_then_no_update() {
        // Arrange
        VendorInventoryQualityUpdatePolicy mockPolicy = mock(VendorInventoryQualityUpdatePolicy.class);
        VendorInventory systemUnderTest = new VendorInventory(new ArrayList<>());

        // Act
        systemUnderTest.updateDaily(mockPolicy);

        // Assert
        verifyNoInteractions(mockPolicy);
    }
}
