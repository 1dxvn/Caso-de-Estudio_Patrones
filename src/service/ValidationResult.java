package service;

public class ValidationResult {

    private String status;
    private String cause;
    private double totalInclusion;
    private double protein;
    private double lipids;
    private double proteinDeviation;
    private double lipidsDeviation;

    public ValidationResult(String status, String cause, double totalInclusion, double protein, double lipids,
            double proteinDeviation, double lipidsDeviation) {
        this.status = status;
        this.cause = cause;
        this.totalInclusion = totalInclusion;
        this.protein = protein;
        this.lipids = lipids;
        this.proteinDeviation = proteinDeviation;
        this.lipidsDeviation = lipidsDeviation;
    }

    public String getStatus() {
        return status;
    }

    public String getCause() {
        return cause;
    }

    public double getTotalInclusion() {
        return totalInclusion;
    }

    public double getProtein() {
        return protein;
    }

    public double getLipids() {
        return lipids;
    }

    public double getProteinDeviation() {
        return proteinDeviation;
    }

    public double getLipidsDeviation() {
        return lipidsDeviation;
    }

    public boolean isApproved() {
        return status.equals("APROBADA");
    }
}
