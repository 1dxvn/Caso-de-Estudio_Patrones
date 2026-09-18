package line;

public class TilapiaProcess implements ExtrusionProcess {

    public int getTemperature() {
        return 125;
    }

    public int getPressure() {
        return 25;
    }

    public int getConditioning() {
        return 180;
    }

    public String getFloatability() {
        return "flotante";
    }
}
