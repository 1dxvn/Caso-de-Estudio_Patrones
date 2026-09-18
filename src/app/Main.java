package app;

import java.util.ArrayList;
import java.util.Locale;
import formula.Formula;
import formula.Ingredient;
import formula.IngredientCatalog;
import line.ExtrusionProcess;
import line.LineFactory;
import line.NutritionProfile;
import line.Packaging;
import line.ShrimpFactory;
import line.TilapiaFactory;
import line.TroutFactory;
import order.ProductionOrder;
import service.CostCalculator;
import service.Format;
import service.FormulaValidator;
import service.MaterialLine;
import service.ProductionScheduler;
import service.ValidationResult;

public class Main {

    public static void main(String[] args) {

        LineFactory tilapia = new TilapiaFactory();
        LineFactory trout = new TroutFactory();
        LineFactory shrimp = new ShrimpFactory();

        FormulaValidator validator = new FormulaValidator();
        CostCalculator calculator = new CostCalculator();
        ProductionScheduler scheduler = new ProductionScheduler();

        System.out.println("=== PLANTA DE ALIMENTO BALANCEADO - ACUICULTURA ===");
        System.out.println();
        System.out.println("LINEAS DISPONIBLES");
        printLine(tilapia);
        printLine(shrimp);
        System.out.println();

        LineFactory selectedLine = trout;
        NutritionProfile profile = selectedLine.createNutritionProfile();
        ExtrusionProcess process = selectedLine.createExtrusionProcess();
        Packaging packaging = selectedLine.createPackaging();

        System.out.printf(Locale.US, "LINEA: %s | objetivo prot %.1f %% lip %.1f %% | pellet %.1f mm%n",
                selectedLine.getLineName(), profile.getProtein(), profile.getLipids(), profile.getPelletSize());
        System.out.printf(Locale.US, "Extrusion %d C / %d bar / %d s (%s) | %s%n", process.getTemperature(),
                process.getPressure(), process.getConditioning(), process.getFloatability(), packaging.getMaterial());
        System.out.println();

        Formula master = new Formula("TRUCHA-M01");
        master.addIngredient(IngredientCatalog.fishMeal(38.0));
        master.addIngredient(IngredientCatalog.soyProteinConcentrate(20.0));
        master.addIngredient(IngredientCatalog.soybeanMeal(10.0));
        master.addIngredient(IngredientCatalog.cornMeal(10.0));
        master.addIngredient(IngredientCatalog.riceBran(5.0));
        master.addIngredient(IngredientCatalog.fishOil(13.0));
        master.addIngredient(IngredientCatalog.vitaminPremix(4.0));

        ValidationResult masterResult = validator.validate(master, profile);

        System.out.println("FORMULA MAESTRA " + master.getCode());
        printIngredients(master);
        printSummary(masterResult);
        System.out.println("Costo: $ " + Format.money(calculator.costPerTon(master)) + " / tonelada");
        System.out.println();

        Formula variantOne = master.clone();
        variantOne.setVariantCode("V-01");
        variantOne.setAdjustReason("alza harina de pescado");
        variantOne.changeInclusion("Harina de pescado", 18.0);
        variantOne.changeInclusion("Concentrado proteico de soya", 40.0);
        variantOne.changeInclusion("Harina de maiz", 8.5);
        variantOne.changeInclusion("Aceite de pescado", 14.5);

        Formula variantTwo = master.clone();
        variantTwo.setVariantCode("V-02");
        variantTwo.setAdjustReason("prueba gluten");
        variantTwo.replaceIngredient("Torta de soya", IngredientCatalog.wheatGluten(10.0));

        ValidationResult resultOne = validator.validate(variantOne, profile);
        ValidationResult resultTwo = validator.validate(variantTwo, profile);

        printVariant(variantOne, resultOne);
        printVariant(variantTwo, resultTwo);
        System.out.println();

        System.out.printf(Locale.US, "Verificacion maestra %s -> Harina de pescado %.2f %% (intacta)%n",
                master.getCode(), master.getInclusionOf("Harina de pescado"));
        System.out.println();

        ProductionOrder order = new ProductionOrder.Builder()
                .orderNumber("OP-2026-0311")
                .scheduledDate("2026-03-11")
                .productionLine(selectedLine)
                .formula(variantOne)
                .tons(60)
                .silo("S-04")
                .batch("LT-TRU-0311")
                .addAdditive("pigmento astaxantina")
                .qualityManager("Laura Benavides")
                .client("Piscicola El Encano")
                .shift("TURNO 1")
                .priority("ALTA")
                .notes("Revisar estabilidad del pellet")
                .build();

        System.out.printf(Locale.US, "ORDEN DE PRODUCCION %s | %.0f t | variante %s | silo %s%n",
                order.getOrderNumber(), order.getTons(), order.getFormula().getVariantCode(), order.getSilo());
        System.out.println("Fecha " + order.getScheduledDate() + " | lote " + order.getBatch() + " | aditivos "
                + order.getSpecialAdditives() + " | calidad " + order.getQualityManager());
        System.out.println();

        System.out.println("REQUERIMIENTO DE MATERIAS PRIMAS");
        ArrayList<MaterialLine> materials = calculator.rawMaterials(order.getFormula(), order.getTons());
        for (int i = 0; i < materials.size(); i++) {
            MaterialLine material = materials.get(i);
            System.out.printf(Locale.US, "  %-32s %12s kg%n", material.getName(), Format.money(material.getKilograms()));
        }
        System.out.println();

        double total = calculator.totalCost(order.getFormula(), order.getTons());
        System.out.println("Costo total de la orden: $ " + Format.money(total));

        double hours = scheduler.extrusionHours(order.getTons(), selectedLine);
        System.out.printf(Locale.US, "Rendimiento real: %.2f t/h -> %d h %d min de extrusion -> %d turnos%n",
                scheduler.realYield(selectedLine), scheduler.wholeHours(hours), scheduler.remainingMinutes(hours),
                scheduler.shiftsNeeded(hours));
        System.out.println("Sacos a producir: " + Format.money(scheduler.bagsNeeded(order.getTons(), selectedLine))
                + " sacos de " + packaging.getBagWeight() + " kg");
        System.out.println();

        try {
            new ProductionOrder.Builder()
                    .orderNumber("OP-2026-0312")
                    .scheduledDate("2026-03-12")
                    .productionLine(selectedLine)
                    .formula(variantOne)
                    .tons(250)
                    .silo("S-02")
                    .batch("LT-TRU-0312")
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("[ERROR CONTROLADO] " + e.getMessage());
        }

        try {
            new ProductionOrder.Builder()
                    .orderNumber("OP-2026-0313")
                    .scheduledDate("2026-03-13")
                    .productionLine(selectedLine)
                    .formula(variantOne)
                    .tons(40)
                    .silo("S-06")
                    .batch("LT-TRU-0313")
                    .addAdditive("probiotico")
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("[ERROR CONTROLADO] " + e.getMessage());
        }
        System.out.println();

        double masterCost = calculator.costPerTon(master);

        System.out.println("COMPARATIVO DE VARIANTES");
        System.out.printf(Locale.US, "%-10s %8s %8s %14s   %s%n", "VARIANTE", "PROT", "LIP", "COSTO/t", "DIFERENCIA");
        System.out.printf(Locale.US, "%-10s %8.2f %8.2f %14s   %s%n", "MAESTRA", masterResult.getProtein(),
                masterResult.getLipids(), "$" + Format.money(masterCost), "--");
        printComparison(variantOne, resultOne, calculator.costPerTon(variantOne), masterCost);
        printComparison(variantTwo, resultTwo, calculator.costPerTon(variantTwo), masterCost);
    }

    public static void printLine(LineFactory productionLine) {
        NutritionProfile profile = productionLine.createNutritionProfile();
        ExtrusionProcess process = productionLine.createExtrusionProcess();
        Packaging packaging = productionLine.createPackaging();
        System.out.printf(Locale.US, "  %-18s prot %.1f %% lip %.1f %% | %d C / %d bar (%s) | %s%n",
                productionLine.getLineName(), profile.getProtein(), profile.getLipids(), process.getTemperature(),
                process.getPressure(), process.getFloatability(), packaging.getMaterial());
    }

    public static void printIngredients(Formula formula) {
        ArrayList<Ingredient> ingredients = formula.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            System.out.printf(Locale.US, "  %-32s %6.2f %%%n", ingredient.getName(), ingredient.getInclusion());
        }
    }

    public static void printSummary(ValidationResult result) {
        if (result.getStatus().equals("APROBADA")) {
            System.out.printf(Locale.US, "  SUMA %.2f %% -> prot %.2f %% lip %.2f %% -> APROBADA (desv %s / %s)%n",
                    result.getTotalInclusion(), result.getProtein(), result.getLipids(),
                    Format.signed(result.getProteinDeviation()), Format.signed(result.getLipidsDeviation()));
        } else if (result.getStatus().equals("FUERA DE ESPECIFICACION")) {
            System.out.printf(Locale.US,
                    "  SUMA %.2f %% -> prot %.2f %% lip %.2f %% -> FUERA DE ESPECIFICACION (desv %s / %s)%n",
                    result.getTotalInclusion(), result.getProtein(), result.getLipids(),
                    Format.signed(result.getProteinDeviation()), Format.signed(result.getLipidsDeviation()));
        } else {
            System.out.printf(Locale.US, "  SUMA %.2f %% -> RECHAZADA %s%n", result.getTotalInclusion(),
                    result.getCause());
        }
    }

    public static void printVariant(Formula variant, ValidationResult result) {
        if (result.getStatus().equals("APROBADA")) {
            System.out.printf(Locale.US, "VARIANTE %s (motivo: %s) -> APROBADA prot %.2f lip %.2f%n",
                    variant.getVariantCode(), variant.getAdjustReason(), result.getProtein(), result.getLipids());
        } else if (result.getStatus().equals("FUERA DE ESPECIFICACION")) {
            System.out.printf(Locale.US,
                    "VARIANTE %s (motivo: %s) -> FUERA DE ESPECIFICACION prot %.2f lip %.2f (desv %s / %s)%n",
                    variant.getVariantCode(), variant.getAdjustReason(), result.getProtein(), result.getLipids(),
                    Format.signed(result.getProteinDeviation()), Format.signed(result.getLipidsDeviation()));
        } else {
            System.out.println("VARIANTE " + variant.getVariantCode() + " (motivo: " + variant.getAdjustReason()
                    + ") -> RECHAZADA " + result.getCause());
        }
    }

    public static void printComparison(Formula variant, ValidationResult result, double cost, double masterCost) {
        if (result.isApproved()) {
            double difference = (cost - masterCost) / masterCost * 100;
            String label = "(sobrecosto)";
            if (difference < 0) {
                label = "(ahorro)";
            }
            System.out.printf(Locale.US, "%-10s %8.2f %8.2f %14s   %s %% %s%n", variant.getVariantCode(),
                    result.getProtein(), result.getLipids(), "$" + Format.money(cost), Format.signed(difference), label);
        } else {
            System.out.printf(Locale.US, "%-10s %8.2f %8.2f %14s   %s%n", variant.getVariantCode(),
                    result.getProtein(), result.getLipids(), "$" + Format.money(cost), result.getStatus());
        }
    }
}
