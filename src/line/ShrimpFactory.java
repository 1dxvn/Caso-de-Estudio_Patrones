package line;

public class ShrimpFactory implements LineFactory {

    public String getLineName() {
        return "CAMARON_CULTIVO";
    }

    public double getCapacityFactor() {
        return 0.70;
    }

    public NutritionProfile createNutritionProfile() {
        return new ShrimpProfile();
    }

    public ExtrusionProcess createExtrusionProcess() {
        return new ShrimpProcess();
    }

    public Packaging createPackaging() {
        return new ShrimpPackaging();
    }
}
