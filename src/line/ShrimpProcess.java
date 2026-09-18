package line;

public class ShrimpProcess implements ExtrusionProcess {

    public int getTemperature() {
        return 110;
    }

    public int getPressure() {
        return 18;
    }

    public int getConditioning() {
        return 300;
    }

    public String getFloatability() {
        return "hundible estable 2 h";
    }
}
