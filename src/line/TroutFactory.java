package line;

public class TroutFactory implements LineFactory {

    public String getLineName() {
        return "TRUCHA_ENGORDE";
    }

    public double getCapacityFactor() {
        return 0.85;
    }

    public NutritionProfile createNutritionProfile() {
        return new TroutProfile();
    }

    public ExtrusionProcess createExtrusionProcess() {
        return new TroutProcess();
    }

    public Packaging createPackaging() {
        return new TroutPackaging();
    }
}
