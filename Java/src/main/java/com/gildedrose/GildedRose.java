package com.gildedrose;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals(ItemConstants.AGED_BRIE)
                    && !items[i].name.equals(ItemConstants.BACKSTAGE_PASS)) {
                if (items[i].quality > ItemConstants.MIN_NORMAL_QUALITY) {
                    if (!items[i].name.equals(ItemConstants.SULFURAS)) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < ItemConstants.MAX_NORMAL_QUALITY) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals(ItemConstants.BACKSTAGE_PASS)) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < ItemConstants.MAX_NORMAL_QUALITY) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < ItemConstants.MAX_NORMAL_QUALITY) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals(ItemConstants.SULFURAS)) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < ItemConstants.MIN_NORMAL_QUALITY) {
                if (!items[i].name.equals(ItemConstants.AGED_BRIE)) {
                    if (!items[i].name.equals(ItemConstants.BACKSTAGE_PASS)) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals(ItemConstants.SULFURAS)) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < ItemConstants.MAX_NORMAL_QUALITY) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }
}
