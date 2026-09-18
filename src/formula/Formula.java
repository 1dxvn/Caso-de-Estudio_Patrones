package formula;

import java.util.ArrayList;

public class Formula implements Cloneable {

    private String code;
    private String variantCode;
    private String adjustReason;
    private ArrayList<Ingredient> ingredients;

    public Formula(String code) {
        this.code = code;
        this.variantCode = "";
        this.adjustReason = "";
        this.ingredients = new ArrayList<Ingredient>();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVariantCode() {
        return variantCode;
    }

    public void setVariantCode(String variantCode) {
        this.variantCode = variantCode;
    }

    public String getAdjustReason() {
        return adjustReason;
    }

    public void setAdjustReason(String adjustReason) {
        this.adjustReason = adjustReason;
    }

    public void changeInclusion(String ingredientName, double newInclusion) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().equals(ingredientName)) {
                ingredients.get(i).setInclusion(newInclusion);
            }
        }
    }

    public void replaceIngredient(String oldName, Ingredient newIngredient) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().equals(oldName)) {
                ingredients.set(i, newIngredient);
            }
        }
    }

    public double getInclusionOf(String ingredientName) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().equals(ingredientName)) {
                return ingredients.get(i).getInclusion();
            }
        }
        return 0.0;
    }

    public Formula clone() {
        Formula copy = new Formula(this.code);
        copy.setVariantCode(this.variantCode);
        copy.setAdjustReason(this.adjustReason);
        for (int i = 0; i < ingredients.size(); i++) {
            copy.addIngredient(ingredients.get(i).copy());
        }
        return copy;
    }
}
