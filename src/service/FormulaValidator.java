package service;

import java.util.ArrayList;
import formula.Formula;
import formula.Ingredient;
import line.NutritionProfile;

public class FormulaValidator {

    public ValidationResult validate(Formula formula, NutritionProfile profile) {
        ArrayList<Ingredient> ingredients = formula.getIngredients();

        double total = 0;
        double protein = 0;
        double lipids = 0;

        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            total = total + ingredient.getInclusion();
            protein = protein + ingredient.getProtein() * ingredient.getInclusion() / 100.0;
            lipids = lipids + ingredient.getLipids() * ingredient.getInclusion() / 100.0;
        }

        total = round2(total);
        protein = round2(protein);
        lipids = round2(lipids);

        double proteinDeviation = round2(protein - profile.getProtein());
        double lipidsDeviation = round2(lipids - profile.getLipids());

        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            if (!ingredient.isAvailable()) {
                return new ValidationResult("RECHAZADA", "INSUMO_NO_DISPONIBLE: " + ingredient.getName(), total,
                        protein, lipids, proteinDeviation, lipidsDeviation);
            }
        }

        if (Math.abs(total - 100.0) > 0.01) {
            return new ValidationResult("RECHAZADA", "SUMA_INVALIDA: " + total + " %", total, protein, lipids,
                    proteinDeviation, lipidsDeviation);
        }

        if (Math.abs(proteinDeviation) > 1.0 || Math.abs(lipidsDeviation) > 1.0) {
            return new ValidationResult("FUERA DE ESPECIFICACION", "", total, protein, lipids, proteinDeviation,
                    lipidsDeviation);
        }

        return new ValidationResult("APROBADA", "", total, protein, lipids, proteinDeviation, lipidsDeviation);
    }

    public double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
