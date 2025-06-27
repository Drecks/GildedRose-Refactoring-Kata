package com.gildedrose.domain.vendor.item.quality.policies.strategies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class DynamicQualityUpdateRateTimeline {

    private final ArrayList<Step> steps;
    public DynamicQualityUpdateRateTimeline() {
        steps = new ArrayList<>();
    }

    public void setRateBelowExpirationDays(int rate, int days)
    {
        steps.removeIf(step -> step.getBelowExpirationDays() == days);
        steps.add(new Step(days, rate));
        steps.sort(Comparator.comparingInt(Step::getBelowExpirationDays));
    }

    public Optional<Integer> getRate(int expirationDays)
    {
        for (Step step : steps) {
            if (expirationDays < step.getBelowExpirationDays()) {
                return Optional.of(step.getRate());
            }
        }
        return Optional.empty();
    }

    private static class Step {
        private final int belowExpirationDays;
        private final int rate;
        public Step (int belowExpirationDays, int rate) {
            this.belowExpirationDays = belowExpirationDays;
            this.rate = rate;
        }

        int getBelowExpirationDays() {
            return belowExpirationDays;
        }
        int getRate()
        {
            return rate;
        }
    }
}
