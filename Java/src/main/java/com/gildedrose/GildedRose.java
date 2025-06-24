package com.gildedrose;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if(items[i].name.equals(ItemConstants.SULFURAS))
            {
               continue;
            }
            boolean itemQualityIncreases = items[i].name.equals(ItemConstants.AGED_BRIE) || items[i].name.equals(ItemConstants.BACKSTAGE_PASS);
            if(itemQualityIncreases)
            {
                int qualityIncrease = 1;

                if(items[i].name.equals(ItemConstants.BACKSTAGE_PASS))
                {
                   if(items[i].sellIn < 11)
                   {
                       qualityIncrease = 2;
                   }
                   if (items[i].sellIn < 6) {
                       qualityIncrease = 3;
                   }
                }
                items[i].quality = Math.min(ItemConstants.MAX_NORMAL_QUALITY, items[i].quality + qualityIncrease);
            }
            else
            {
                items[i].quality = Math.max(ItemConstants.MIN_NORMAL_QUALITY, items[i].quality - 1);
            }

            items[i].sellIn = items[i].sellIn - 1;

            if(items[i].sellIn < 0)
            {
                if(items[i].name.equals(ItemConstants.BACKSTAGE_PASS))
                {
                    items[i].quality = ItemConstants.MIN_NORMAL_QUALITY;
                }
                else if (items[i].name.equals(ItemConstants.AGED_BRIE))
                {
                    items[i].quality = Math.min(ItemConstants.MAX_NORMAL_QUALITY, items[i].quality + 1);
                }
                else
                {
                    items[i].quality = Math.max(ItemConstants.MIN_NORMAL_QUALITY, items[i].quality - 1);
                }
            }
        }
    }
}
