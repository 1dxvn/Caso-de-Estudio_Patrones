package order;

import java.util.ArrayList;
import formula.Formula;
import line.LineFactory;

public class ProductionOrder {

    private final String orderNumber;
    private final String scheduledDate;
    private final LineFactory productionLine;
    private final Formula formula;
    private final double tons;
    private final String silo;
    private final String batch;
    private final ArrayList<String> specialAdditives;
    private final String client;
    private final String shift;
    private final String notes;
    private final String priority;
    private final String qualityManager;

    private ProductionOrder(Builder builder) {
        this.orderNumber = builder.orderNumber;
        this.scheduledDate = builder.scheduledDate;
        this.productionLine = builder.productionLine;
        this.formula = builder.formula;
        this.tons = builder.tons;
        this.silo = builder.silo;
        this.batch = builder.batch;
        this.specialAdditives = new ArrayList<String>(builder.specialAdditives);
        this.client = builder.client;
        this.shift = builder.shift;
        this.notes = builder.notes;
        this.priority = builder.priority;
        this.qualityManager = builder.qualityManager;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public LineFactory getProductionLine() {
        return productionLine;
    }

    public Formula getFormula() {
        return formula;
    }

    public double getTons() {
        return tons;
    }

    public String getSilo() {
        return silo;
    }

    public String getBatch() {
        return batch;
    }

    public ArrayList<String> getSpecialAdditives() {
        return new ArrayList<String>(specialAdditives);
    }

    public String getClient() {
        return client;
    }

    public String getShift() {
        return shift;
    }

    public String getNotes() {
        return notes;
    }

    public String getPriority() {
        return priority;
    }

    public String getQualityManager() {
        return qualityManager;
    }

    public static class Builder {

        private String orderNumber;
        private String scheduledDate;
        private LineFactory productionLine;
        private Formula formula;
        private double tons;
        private String silo;
        private String batch;
        private ArrayList<String> specialAdditives = new ArrayList<String>();
        private String client = "";
        private String shift = "";
        private String notes = "";
        private String priority = "NORMAL";
        private String qualityManager = "";

        public Builder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public Builder scheduledDate(String scheduledDate) {
            this.scheduledDate = scheduledDate;
            return this;
        }

        public Builder productionLine(LineFactory productionLine) {
            this.productionLine = productionLine;
            return this;
        }

        public Builder formula(Formula formula) {
            this.formula = formula;
            return this;
        }

        public Builder tons(double tons) {
            this.tons = tons;
            return this;
        }

        public Builder silo(String silo) {
            this.silo = silo;
            return this;
        }

        public Builder batch(String batch) {
            this.batch = batch;
            return this;
        }

        public Builder addAdditive(String additive) {
            this.specialAdditives.add(additive);
            return this;
        }

        public Builder client(String client) {
            this.client = client;
            return this;
        }

        public Builder shift(String shift) {
            this.shift = shift;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public Builder qualityManager(String qualityManager) {
            this.qualityManager = qualityManager;
            return this;
        }

        public ProductionOrder build() {
            if (orderNumber == null || orderNumber.equals("")) {
                throw new IllegalStateException("Falta el numero de orden");
            }
            if (scheduledDate == null || scheduledDate.equals("")) {
                throw new IllegalStateException("Falta la fecha programada");
            }
            if (productionLine == null) {
                throw new IllegalStateException("Falta la linea de produccion");
            }
            if (formula == null) {
                throw new IllegalStateException("Falta la formula");
            }
            if (silo == null || silo.equals("")) {
                throw new IllegalStateException("Falta el silo de destino");
            }
            if (batch == null || batch.equals("")) {
                throw new IllegalStateException("Falta el lote de trazabilidad");
            }
            if (tons < 5 || tons > 200) {
                throw new IllegalStateException("Toneladas fuera de rango permitido (5 a 200): " + tons);
            }
            if (specialAdditives.size() > 0 && qualityManager.equals("")) {
                throw new IllegalStateException("Se declararon aditivos especiales sin responsable de calidad");
            }
            return new ProductionOrder(this);
        }
    }
}
