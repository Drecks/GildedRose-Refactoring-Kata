package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    @Test
    void updateQuality_when_sellIn_gt_0_then_sellIn_and_quality_decreases() {
        // Arrange
        Item[] items = new Item[]{new Item("Normal Item", 10, 20)};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(9, items[0].sellIn);
        assertEquals(19, items[0].quality);
    }

    @Test
    void updateQuality_when_quality_eq_0_then_quality_does_not_decrease() {
        // Arrange
        Item[] items = new Item[]{new Item("Normal Item", 5, 0)};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(4, items[0].sellIn);
        assertEquals(0, items[0].quality);
    }

    @Test
    public void updateQuality_quality_eq_50_then_quality_does_not_increase_above_50() {
        // Arrange
        Item[] items = new Item[]{new Item(ItemConstants.AGED_BRIE, 5, 50)};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(4, items[0].sellIn);
        assertEquals(50, items[0].quality);
    }

    @Test
    public void updateQuality_when_sellIn_lte_0_then_quality_decreases_twice() {
        // Arrange
        Item[] items = new Item[]{new Item("Normal Item", 0, 10)};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(-1, items[0].sellIn);
        assertEquals(8, items[0].quality);
    }

    @Test
    public void updateQuality_when_item_is_aged_brie_then_quality_increases() {
        // Arrange
        Item[] items = new Item[]{new Item(ItemConstants.AGED_BRIE, 2, 0)};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(1, items[0].sellIn);
        assertEquals(1, items[0].quality);
    }

    @Test
    public void updateQuality_when_item_is_sulfuras_then_quality_does_not_decrease() {
        // Arrange
        Item[] items = new Item[]{new Item(ItemConstants.SULFURAS, 0, ItemConstants.LEGENDARY_QUALITY)};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(0, items[0].sellIn);
        assertEquals(80, items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
        "15, 10, 11",
        "10, 10, 12",
        "5, 10, 13",
        "-1, 10, 0",
        "15, 50, 50"
    })
    public void updateQuality_when_quality_is_backstage_passes_and_then_quality_increases(int sellIn, int qualityBeforeUpdate, int qualityAfterUpdate) {
        // Arrange
        Item[] items = new Item[]{new Item(ItemConstants.BACKSTAGE_PASS, sellIn, qualityBeforeUpdate)};
        GildedRose app = new GildedRose(items);

        // Act
        app.updateQuality();

        // Assert
        assertEquals(sellIn - 1, items[0].sellIn);
        assertEquals(qualityAfterUpdate, items[0].quality);
    }
}
