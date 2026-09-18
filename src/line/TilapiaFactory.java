package line;

public class TilapiaFactory implements LineFactory {

    public String getLineName() {
        return "TILAPIA_ENGORDE";
    }

    public double getCapacityFactor() {
        return 1.00;
    }

    public NutritionProfile createNutritionProfile() {
        return new TilapiaProfile();
    }

    public ExtrusionProcess createExtrusionProcess() {
        return new TilapiaProcess();
    }

    public Packaging createPackaging() {
        return new TilapiaPackaging();
    }
}
