package com.gildedrose;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @ParameterizedTest
    @CsvSource({
        // sellIn, qualityBefore, qualityAfter, sellInAfter
        "10, 20, 19, 9",   // normal degradation
        "5, 0, 0, 4",      // quality floor
        "0, 10, 8, -1",    // expired item: degrades twice
    })
    void updateQuality_when_normalItem_then_quality_degrades_over_time(int sellIn, int qualityBefore, int expectedQuality, int expectedSellIn) {
        //ARRANGE
        Item[] items = new Item[]{new Item("Normal Item", sellIn, qualityBefore)};
        GildedRose systemUnderTest = new GildedRose(items);

        //ACT
        systemUnderTest.updateQuality();

        //ASSERT
        assertEquals(expectedSellIn, items[0].sellIn);
        assertEquals(expectedQuality, items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
        // sellIn, qualityBefore, qualityAfter, sellInAfter
        "2, 0, 1, 1",       // quality increases
        "0, 0, 2, -1",      // quality increases twice when item expires
        "-1, 2, 4, -2",     // quality increases twice when item is expired
        "5, 50, 50, 4",     // quality capped at 50
        "-1, 50, 50, -2"    // quality capped at 50 when item is expired
    })
    void updateQuality_when_agedBrie_then_quality_increases_over_time(int sellIn, int qualityBefore, int expectedQuality, int expectedSellIn) {
        //ARRANGE
        Item[] items = new Item[]{new Item(ItemConstants.AGED_BRIE, sellIn, qualityBefore)};
        GildedRose systemUnderTest = new GildedRose(items);

        //ACT
        systemUnderTest.updateQuality();

        //ASSERT
        assertEquals(expectedSellIn, items[0].sellIn);
        assertEquals(expectedQuality, items[0].quality);
    }


    @ParameterizedTest
    @CsvSource({
        // sellIn, qualityBefore, qualityAfter, sellInAfter
        "0, 80, 80, 0",     // legendary: no change
        "-1, 80, 80, -1"    // even expired
    })
    void updateQuality_when_sulfuras_then_quality_and_sellIn_does_not_change(int sellIn, int qualityBefore, int expectedQuality, int expectedSellIn) {
        //ARRANGE
        Item[] items = new Item[]{new Item(ItemConstants.SULFURAS, sellIn, qualityBefore)};
        GildedRose systemUnderTest = new GildedRose(items);

        //ACT
        systemUnderTest.updateQuality();

        //ASSERT
        assertEquals(expectedSellIn, items[0].sellIn);
        assertEquals(expectedQuality, items[0].quality);
    }


    @ParameterizedTest
    @CsvSource({
        // sellIn, qualityBefore, qualityAfter, sellInAfter
        "15, 10, 11, 14",   //quality increase by 1 when expires in > 10
        "10, 10, 12, 9",    //quality increases by 2 when expires in < 10
        "5, 10, 13, 4",     //quality increases by 3 when expires in < 5
        "0, 10, 0, -1",     // quality drops to 0 when item expires
        "-1, 10, 0, -2",    // quality stays 0 when item is expired
        "5, 50, 50, 4"      //quality doesn't increase higher than 50
    })
    void updateQuality_when_backstagePasses_then_quality_change_rate_increases_over_time(int sellIn, int qualityBefore, int expectedQuality, int expectedSellIn) {
        //ARRANGE
        Item[] items = new Item[]{new Item(ItemConstants.BACKSTAGE_PASS, sellIn, qualityBefore)};
        GildedRose systemUnderTest = new GildedRose(items);

        //ACT
        systemUnderTest.updateQuality();

        //ASSERT
        assertEquals(expectedSellIn, items[0].sellIn);
        assertEquals(expectedQuality, items[0].quality);
    }
}
