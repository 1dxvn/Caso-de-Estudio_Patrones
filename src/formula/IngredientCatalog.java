package formula;

public class IngredientCatalog {

    public static Ingredient fishMeal(double inclusion) {
        return new Ingredient("Harina de pescado", 62.0, 9.0, 7800, true, inclusion);
    }

    public static Ingredient soybeanMeal(double inclusion) {
        return new Ingredient("Torta de soya", 46.0, 1.8, 2950, true, inclusion);
    }

    public static Ingredient soyProteinConcentrate(double inclusion) {
        return new Ingredient("Concentrado proteico de soya", 62.5, 2.0, 5400, true, inclusion);
    }

    public static Ingredient cornMeal(double inclusion) {
        return new Ingredient("Harina de maiz", 8.5, 3.8, 1650, true, inclusion);
    }

    public static Ingredient wheatGluten(double inclusion) {
        return new Ingredient("Gluten de trigo", 78.0, 1.5, 9100, false, inclusion);
    }

    public static Ingredient fishOil(double inclusion) {
        return new Ingredient("Aceite de pescado", 0.0, 99.0, 11200, true, inclusion);
    }

    public static Ingredient riceBran(double inclusion) {
        return new Ingredient("Salvado de arroz", 13.0, 14.0, 1200, true, inclusion);
    }

    public static Ingredient vitaminPremix(double inclusion) {
        return new Ingredient("Premezcla vitaminico-mineral", 0.0, 0.0, 18500, true, inclusion);
    }
}
