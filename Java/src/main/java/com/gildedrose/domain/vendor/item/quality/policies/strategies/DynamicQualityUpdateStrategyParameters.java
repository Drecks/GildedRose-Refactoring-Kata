package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import com.gildedrose.domain.vendor.item.VendorItem;

import java.util.Optional;

/**
 * Encapsulates the parameters required by the {@link DynamicQualityUpdateStrategy} to update
 * the quality of a {@link VendorItem} dynamically based on its expiration timeline.
 * <p>
 * This parameter object defines:
 * </p>
 * <ul>
 *   <li>{@code initialUpdateRate} — a fallback rate to apply when no specific rate is defined for the current day.</li>
 *   <li>{@code rateTimeline} — a time-sensitive structure that provides day-specific quality change rates.</li>
 *   <li>{@code qualityAfterExpiration} — an optional quality override that, if present, is applied
 *   directly when the item is expired.</li>
 * </ul>
 *
 * <p>
 * This class is immutable and thread-safe. Instances are typically passed directly to a strategy instance,
 * such as {@link DynamicQualityUpdateStrategy#INSTANCE}.
 * </p>
 *
 * <pre>{@code
 * var parameters = new DynamicQualityUpdateStrategyParameters(
 *     -1,
 *     new DynamicQualityUpdateRateTimeline(...),
 *     0
 * );
 * }</pre>
 *
 * @see DynamicQualityUpdateStrategy
 * @see DynamicQualityUpdateRateTimeline
 * @see VendorItem
 */
public final class DynamicQualityUpdateStrategyParameters {
    private final int initialUpdateRate;
    private final DynamicQualityUpdateRateTimeline rateTimeline;
    private final Integer qualityAfterExpiration;

    /**
     * Constructs a new parameter set for use with {@link DynamicQualityUpdateStrategy}.
     *
     * @param initialUpdateRate      the fallback rate to apply if no rate is defined for a given day
     * @param timeline               the timeline providing day-specific update rates; must not be {@code null}
     * @param qualityAfterExpiration an optional fixed quality to apply when the item is expired; may be {@code null}
     */
    public DynamicQualityUpdateStrategyParameters(int initialUpdateRate, DynamicQualityUpdateRateTimeline timeline, Integer qualityAfterExpiration) {
        assert timeline != null;
        this.initialUpdateRate = initialUpdateRate;
        this.rateTimeline = timeline;
        this.qualityAfterExpiration = qualityAfterExpiration;
    }

    /**
     * Returns the default quality change rate to use when no rate is defined
     * in the timeline for the current expiration day.
     *
     * @return the fallback update rate
     */
    public int getInitialRate() {
        return initialUpdateRate;
    }

    /**
     * Returns the rate timeline that provides quality update rates based on the item’s
     * remaining days to expiration.
     *
     * @return the dynamic update rate timeline
     */
    public DynamicQualityUpdateRateTimeline getRateTimeline() {
        return rateTimeline;
    }

    /**
     * Returns an optional fixed quality value to be applied when the item is expired.
     * <p>
     * If present, this value overrides any dynamic or default rate-based updates after expiration.
     * </p>
     *
     * @return an {@link Optional} containing the post-expiration quality override, if any
     */
    public Optional<Integer> getQualityAfterExpiration() {
        return Optional.ofNullable(qualityAfterExpiration);
    }
    @Override
    public String toString() {
        return "DynamicQualityUpdateStrategyParameters{" +
            "initialUpdateRate=" + initialUpdateRate +
            ", rateTimeline=" + rateTimeline +
            ", qualityAfterExpiration=" + qualityAfterExpiration +
            '}';
    }
}
