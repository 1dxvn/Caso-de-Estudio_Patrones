package line;

public interface LineFactory {
    String getLineName();
    double getCapacityFactor();
    NutritionProfile createNutritionProfile();
    ExtrusionProcess createExtrusionProcess();
    Packaging createPackaging();
}
