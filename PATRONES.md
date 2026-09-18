# PATRONES.md


## 1. Abstract Factory

**Ubicacion:** paquete 'line'

**Justificacion:** cada linea de produccion define de forma inseparable el perfil nutricional, el proceso de extrusion y el empaque. Al pedir la familia completa a una sola fabrica es imposible combinar el perfil de tilapia con el proceso de trucha, que fue exactamente el error que costo 32 millones. `FormulaValidator` y `ProductionScheduler` reciben solo las interfaces de producto (`NutritionProfile`, `Packaging`, `LineFactory`), por lo que no existe ningun `switch` sobre la linea para decidir temperatura, tamano de saco o proteina objetivo.

## 2. Prototype

**Ubicacion:** paquete `formula`.

**Justificacion:** el nutricionista parte de la formula maestra y genera variantes para un lote puntual. `Formula.clone()` crea una nueva lista y copia uno por uno los objetos `Ingredient`, es decir clonacion profunda: cambiar un porcentaje con `changeInclusion()` o sustituir un insumo con `replaceIngredient()` en la variante no toca la maestra ni las otras variantes. Esto se evidencia en la salida con la linea "Verificacion maestra TRUCHA-M01 -> Harina de pescado 38.00 % (intacta)", impresa despues de generar V-01 y V-02. La variante guarda su identidad en los campos `variantCode` y `adjustReason`.

## 3. Builder

**Ubicacion:** paquete `order`.


**Justificacion:** la orden tiene siete datos obligatorios y seis opcionales; un constructor con trece parametros seria ilegible y facil de equivocar. El Builder fluido permite armar la orden paso a paso y `ProductionOrder` queda inmutable (atributos `final`, sin setters y copia defensiva de la lista de aditivos). Toda la validacion se concentra en `build()`, que lanza `IllegalStateException` si falta un obligatorio, si las toneladas no estan entre 5 y 200, o si se declararon aditivos especiales sin responsable de calidad. Los dos ultimos casos se demuestran en `Main` dentro de `try/catch`.



## Compilar y ejecutar

```
javac -d bin src/line/*.java src/formula/*.java src/order/*.java src/service/*.java src/app/*.java
java -cp bin app.Main
```
