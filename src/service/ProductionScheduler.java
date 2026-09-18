package service;

import line.LineFactory;

public class ProductionScheduler {

    private double nominalYield = 4.0;
    private double shiftHours = 8.0;
    private double startupHours = 0.75;

    public double realYield(LineFactory productionLine) {
        return nominalYield * productionLine.getCapacityFactor();
    }

    public double extrusionHours(double tons, LineFactory productionLine) {
        return tons / realYield(productionLine);
    }

    public int wholeHours(double hours) {
        return (int) hours;
    }

    public int remainingMinutes(double hours) {
        return (int) Math.round((hours - (int) hours) * 60);
    }

    public int shiftsNeeded(double hours) {
        double usefulHours = shiftHours - startupHours;
        return (int) Math.ceil(hours / usefulHours);
    }

    public int bagsNeeded(double tons, LineFactory productionLine) {
        double kilograms = tons * 1000;
        return (int) Math.ceil(kilograms / productionLine.createPackaging().getBagWeight());
    }
}
