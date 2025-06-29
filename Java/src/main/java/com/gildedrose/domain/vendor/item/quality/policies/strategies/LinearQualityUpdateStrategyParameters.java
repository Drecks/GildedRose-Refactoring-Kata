package com.gildedrose.domain.vendor.item.quality.policies.strategies;

/**
 * Encapsulates the parameters for the {@link LinearQualityUpdateStrategy}, defining how
 * an item's quality should change before and after expiration.
 */
public final class LinearQualityUpdateStrategyParameters {
    private final int rate;
    private final int expiredRate;

    /**
     * Constructs a new set of parameters for linear quality updates.
     *
     * @param rate        the rate to apply when the item is not expired
     * @param expiredRate the rate to apply after the item has expired
     */
    public LinearQualityUpdateStrategyParameters(int rate, int expiredRate) {
        this.rate = rate;
        this.expiredRate = expiredRate;
    }

    /**
     * @return the quality update rate to apply before expiration
     */
    public int getRate() {
        return rate;
    }

    /**
     * @return the quality update rate to apply after expiration
     */
    public int getExpiredRate() {
        return expiredRate;
    }
    @Override
    public String toString() {
        return "LinearQualityUpdateStrategyParameters{" +
            "rate=" + rate +
            ", expiredRate=" + expiredRate +
            '}';
    }
}
