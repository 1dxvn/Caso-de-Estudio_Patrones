package formula;

public class Ingredient implements Cloneable {

    private String name;
    private double protein;
    private double lipids;
    private double cost;
    private boolean available;
    private double inclusion;

    public Ingredient(String name, double protein, double lipids, double cost, boolean available, double inclusion) {
        this.name = name;
        this.protein = protein;
        this.lipids = lipids;
        this.cost = cost;
        this.available = available;
        this.inclusion = inclusion;
    }

    public String getName() {
        return name;
    }

    public double getProtein() {
        return protein;
    }

    public double getLipids() {
        return lipids;
    }

    public double getCost() {
        return cost;
    }

    public boolean isAvailable() {
        return available;
    }

    public double getInclusion() {
        return inclusion;
    }

    public void setInclusion(double inclusion) {
        this.inclusion = inclusion;
    }

    public Ingredient copy() {
        return new Ingredient(name, protein, lipids, cost, available, inclusion);
    }
}
