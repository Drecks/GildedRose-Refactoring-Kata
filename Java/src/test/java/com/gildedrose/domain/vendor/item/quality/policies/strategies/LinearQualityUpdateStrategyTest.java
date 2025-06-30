package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import com.gildedrose.Item;
import com.gildedrose.domain.vendor.item.VendorItem;
import com.gildedrose.domain.vendor.item.VendorItemKind;
import com.gildedrose.domain.vendor.item.VendorItemRarity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearQualityUpdateStrategyTest {

    private final LinearQualityUpdateStrategy systemUnderTest = LinearQualityUpdateStrategy.INSTANCE;

    private VendorItem createItem(int sellIn, int quality) {
        return new VendorItem(
            new Item("Linear Item", sellIn, quality),
            VendorItemRarity.Common,
            VendorItemKind.Default,
            0,
            50
        );
    }

    @Test
    void execute_when_item_not_expired_then_applies_regular_rate() {
        //ARRANGE
        VendorItem item = createItem(5, 10);
        LinearQualityUpdateStrategyParameters params = new LinearQualityUpdateStrategyParameters(3, 5);

        //ACT
        systemUnderTest.execute(item, params);

        //ASSERT
        assertEquals(13, item.getQuality());
    }

    @Test
    void execute_when_item_expired_then_applies_expired_rate() {
        //ARRANGE
        VendorItem item = createItem(-1, 10);
        LinearQualityUpdateStrategyParameters params = new LinearQualityUpdateStrategyParameters(3, 5);

        //ACT
        systemUnderTest.execute(item, params);

        //ASSERT
        assertEquals(15, item.getQuality());
    }

    @Test
    void execute_then_quality_does_not_exceed_maximum() {
        //ARRANGE
        VendorItem item = createItem(5, 48);
        LinearQualityUpdateStrategyParameters params = new LinearQualityUpdateStrategyParameters(5, 10);

        //ACT
        systemUnderTest.execute(item, params);

        //ASSERT
        assertEquals(50, item.getQuality());
    }

    @Test
    void execute_then_quality_does_not_drop_below_minimum() {
        //ARRANGE
        VendorItem item = createItem(5, 2);
        LinearQualityUpdateStrategyParameters params = new LinearQualityUpdateStrategyParameters(-5, -10);

        //ACT
        systemUnderTest.execute(item, params);

        //ASSERT
        assertEquals(0, item.getQuality());
    }
}
