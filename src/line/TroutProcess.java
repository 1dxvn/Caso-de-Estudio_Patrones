package line;

public class TroutProcess implements ExtrusionProcess {

    public int getTemperature() {
        return 135;
    }

    public int getPressure() {
        return 32;
    }

    public int getConditioning() {
        return 240;
    }

    public String getFloatability() {
        return "semihundible";
    }
}
