package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import java.util.*;

/**
 * Represents a timeline of quality update rates based on a vendor item's remaining expiration days.
 * <p>
 * Each step defines a quality update rate that applies to items with fewer than a specified number
 * of expiration days. When queried, the timeline returns the first matching rate for which the current
 * expiration days are less than the defined threshold.
 * </p>
 *
 * <p>
 * This class allows dynamic configuration of rate steps using {@link #setRateBelowExpirationDays(int, int)},
 * and provides lookup functionality through {@link #getRate(int)}.
 * </p>
 */
public final class DynamicQualityUpdateRateTimeline {

    private final NavigableMap<Integer, Integer> steps = new TreeMap<>();

    /**
     * Sets or updates a quality change rate for items with fewer expiration days than the specified threshold.
     * <p>
     * If a rate step with the same {@code days} threshold already exists, it will be replaced.
     * Steps are internally ordered by their threshold in ascending order.
     * </p>
     *
     * @param rate the quality change rate to apply
     * @param days the exclusive upper bound on expiration days for this rate (e.g. applies if {@code expirationDays < days})
     */
    public void setRateBelowExpirationDays(int rate, int days) {
        steps.put(days, rate);
    }

    /**
     * Retrieves the applicable quality update rate for a given number of expiration days.
     * <p>
     * The returned rate corresponds to the first step where the specified {@code expirationDays}
     * is less than the step's configured threshold. If no such step exists, an empty result is returned.
     * </p>
     *
     * @param expirationDays the number of days remaining before the item expires
     * @return an {@link Optional} containing the matching rate, or empty if no rate applies
     */
    public Optional<Integer> getRate(int expirationDays) {
        return steps.entrySet().stream()
            .filter(e -> expirationDays < e.getKey())
            .map(Map.Entry::getValue)
            .findFirst();
    }
}
