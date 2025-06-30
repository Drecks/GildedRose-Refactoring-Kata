package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import com.gildedrose.Item;
import com.gildedrose.domain.vendor.item.VendorItem;
import com.gildedrose.domain.vendor.item.VendorItemKind;
import com.gildedrose.domain.vendor.item.VendorItemRarity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicVendorItemQualityStrategyTest {

    private final DynamicQualityUpdateStrategy systemUnderTest = DynamicQualityUpdateStrategy.INSTANCE;

    private VendorItem createItem(int sellIn, int quality, int maxQuality) {
        return new VendorItem(new Item("Dynamic Item", sellIn, quality), VendorItemRarity.Common, VendorItemKind.Default, 0, maxQuality);
    }

    @Test
    void execute_expired_item_with_quality_after_expiration_sets_quality() {
        //ARRANGE
        VendorItem item = createItem(-1, 25, 50);
        DynamicQualityUpdateRateTimeline timeline = new DynamicQualityUpdateRateTimeline();
        DynamicQualityUpdateStrategyParameters params =
            new DynamicQualityUpdateStrategyParameters(5, timeline, 10);

        //ACT
        systemUnderTest.execute(item, params);

        //ASSERT
        assertEquals(10, item.getQuality());
    }

    @Test
    void execute_when_expired_item_without_quality_after_expiration_then_applies_fallback_rate() {
        //ARRANGE
        VendorItem item = createItem(-1, 20, 50);
        DynamicQualityUpdateRateTimeline timeline = new DynamicQualityUpdateRateTimeline();
        DynamicQualityUpdateStrategyParameters params =
            new DynamicQualityUpdateStrategyParameters(-2, timeline, null);

        //ACT
        systemUnderTest.execute(item, params);

        //ASSERT
        assertEquals(18, item.getQuality());
    }

    @Test
    void execute_when_non_expired_item_with_matching_timeline_rate_then_applies_rate() {
        //ARRANGE
        VendorItem item = createItem(4, 20, 50);
        DynamicQualityUpdateRateTimeline timeline = new DynamicQualityUpdateRateTimeline();
        timeline.setRateBelowExpirationDays(3, 5);  // applies if expirationDays < 3
        timeline.setRateBelowExpirationDays(5, 2);  // applies if expirationDays < 5

        DynamicQualityUpdateStrategyParameters params =
            new DynamicQualityUpdateStrategyParameters(1, timeline, null);

        //ACT
        systemUnderTest.execute(item, params);

        //ASSERT
        assertEquals(23, item.getQuality()); // 3 added from timeline
    }

    @Test
    void execute_when_non_expired_item_without_timeline_match_then_applies_initial_rate() {
        //ARRANGE
        VendorItem item = createItem(20, 30, 50);
        DynamicQualityUpdateRateTimeline timeline = new DynamicQualityUpdateRateTimeline(); // empty

        DynamicQualityUpdateStrategyParameters params =
            new DynamicQualityUpdateStrategyParameters(-3, timeline, null);

        //ACT
        systemUnderTest.execute(item, params);

        //ASSERT
        assertEquals(27, item.getQuality());
    }

    @Test
    void execute_when_does_not_exceed_item_quality_bounds() {
        //ARRANGE
        VendorItem item = createItem(5, 14, 15);
        DynamicQualityUpdateRateTimeline timeline = new DynamicQualityUpdateRateTimeline();
        timeline.setRateBelowExpirationDays(10, 5);

        DynamicQualityUpdateStrategyParameters params =
            new DynamicQualityUpdateStrategyParameters(1, timeline, null);

        //ACT
        systemUnderTest.execute(item, params);

        //ASSERT
        assertEquals(15, item.getQuality()); // capped
    }
}
