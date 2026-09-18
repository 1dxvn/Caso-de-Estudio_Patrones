package service;

public class MaterialLine {

    private String name;
    private double kilograms;

    public MaterialLine(String name, double kilograms) {
        this.name = name;
        this.kilograms = kilograms;
    }

    public String getName() {
        return name;
    }

    public double getKilograms() {
        return kilograms;
    }
}
