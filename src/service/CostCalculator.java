package service;

import java.util.ArrayList;
import formula.Formula;
import formula.Ingredient;

public class CostCalculator {

    public double costPerTon(Formula formula) {
        ArrayList<Ingredient> ingredients = formula.getIngredients();
        double cost = 0;
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            cost = cost + ingredient.getInclusion() / 100.0 * ingredient.getCost() * 1000;
        }
        return cost;
    }

    public double totalCost(Formula formula, double tons) {
        return costPerTon(formula) * tons;
    }

    public ArrayList<MaterialLine> rawMaterials(Formula formula, double tons) {
        ArrayList<Ingredient> ingredients = formula.getIngredients();
        ArrayList<MaterialLine> materials = new ArrayList<MaterialLine>();

        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            double kilograms = ingredient.getInclusion() / 100.0 * tons * 1000;
            materials.add(new MaterialLine(ingredient.getName(), kilograms));
        }

        for (int i = 0; i < materials.size(); i++) {
            for (int j = i + 1; j < materials.size(); j++) {
                if (materials.get(j).getKilograms() > materials.get(i).getKilograms()) {
                    MaterialLine temp = materials.get(i);
                    materials.set(i, materials.get(j));
                    materials.set(j, temp);
                }
            }
        }

        return materials;
    }
}
