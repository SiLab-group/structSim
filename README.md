# structSim
Framework for automated simulation to analyze adaptive systems

The Structured Simulation Framework was created for automated simulation to analyze adaptive systems. 

This project is a RCSO project lead by the Si-Lab group of the Institute of Informaiton Systems of the HES-SO Valais/Wallis in Sierre, Switzerland.  

 
To use our framework to your project you have to add the "structSimV1.jar" to your project. 

A tutorial (structSimTutorial.pdf) is available to help you to begin with our framework. Some example are also described in this tutorial. 

## Citation

If you use this framework in your research, please cite the following paper:

> René Schumann, Caroline Taramarcaz. **Towards Systematic Testing of Complex Interacting Systems.** In *Proceedings of the Workshop on Testing Extra-Functional Properties and Quality Characteristics of Software Systems (ITEQS)*, CEUR Workshop Proceedings, Vol. 2397, pp. 55–, 2019. https://ceur-ws.org/Vol-2397/paper8.pdf

If you have some questions/remarks about this project, you can consult the official page of the project at this address :  
https://www.hevs.ch/fr/mini-sites/projets-produits/si-lab/projets/structured-simulation--a-framework-for-the-automated-analysis-of-adaptive-systems-8640 

And in case, don't hesitate to contact us. 

## Running the simulation (mock mode)

The framework ships with a mock simulator (`SimpleSimulationHandler`) that lets you run without a real external simulator.

Ready-to-use example files are in [`examples/simple/`](examples/simple/). Copy them as described below.

### 1. Copy `config.properties` into the classpath

Copy [`examples/simple/config.properties`](examples/simple/config.properties) to `structSimV1/src/main/resources/config.properties`:

```bash
cp examples/simple/config.properties structSimV1/src/main/resources/config.properties
```

```properties
pathOUT = /tmp/structsim-results
pathParameters = parameters.txt
pathSimulator = /tmp/structsim-simulator
pathToSimulatorResultFile = /tmp/structsim-simulator/results/results.txt
cuttOfPlanning = 10
typeCuttOfPlanning = INT
```

| Key | Description |
|-----|-------------|
| `pathOUT` | Folder where results and `SummaryFile.txt` are written |
| `pathParameters` | Name of the parameters file — loaded from the classpath, so place it in `src/main/resources/` |
| `pathSimulator` | Folder where per-simulation sub-folders are created |
| `pathToSimulatorResultFile` | Result file the simulator creates after each run |
| `typeCuttOfPlanning` | When to stop generating simulations — `INT`, `CRITERIA`, `HOURS`, `MINUTES`, or `DAY` |
| `cuttOfPlanning` | Threshold value for the chosen stop type (see below) |

**How `typeCuttOfPlanning` works:**

The framework builds a tree of simulations by applying modifiers on top of each other. This setting controls when it stops growing the tree:

- `INT` — stops after N outer loop iterations (e.g. `10` means 10 rounds of applying all modifiers). Good for a quick bounded run.
- `CRITERIA` — prunes any branch whose cumulative probability drops below the threshold (e.g. `0.15` = stop exploring paths less likely than 15%). Each modifier has a probability; they multiply as modifiers are combined. Good for skipping unlikely scenarios.
- `HOURS` / `MINUTES` / `DAY` — runs for a fixed wall-clock duration regardless of how many simulations are generated.

> **Note:** the file must have the `.properties` extension.

### 2. Copy `parameters.txt` into the classpath

Copy [`examples/simple/parameters.txt`](examples/simple/parameters.txt) to `structSimV1/src/main/resources/parameters.txt`:

```bash
cp examples/simple/parameters.txt structSimV1/src/main/resources/parameters.txt
```

```
val1=1
val2=2
val3=3
val4=4
val5=5
```

One `key=value` pair per line. Values must be numbers.

### 3. Configure modifiers in `Simulation.java`

Open `structSimV1/src/main/java/ch/hevs/silab/structuredsim/gluecode/Simulation.java` and edit the modifiers list. Each `ConcreteModifier` takes `(parameterKey, operator, delta, probability)`:

```java
modifiers.add(new ConcreteModifier("val2", '+', 1.0, 0.5));
modifiers.add(new ConcreteModifier("val2", '+', 10.0, 0.5));
```

Supported operators: `+`, `-`, `*`, `/`

The `probability` field is only used for pruning when `typeCuttOfPlanning = CRITERIA`. With `INT` mode it has no effect on how many simulations run.

### 4. Create output directories and run

```bash
mkdir -p /tmp/structsim-results /tmp/structsim-simulator
cd structSimV1
mvn exec:java -Dexec.mainClass=ch.hevs.silab.structuredsim.gluecode.Simulation
```

Results are written to `pathOUT/SummaryFile.txt`.

---

## Quick start — run the built-in example

The `examples/simple/` directory is a ready-to-run project matching the tutorial. It modifies `val2` with two modifiers (`+1` and `+10`) and runs 10 iterations.

```bash
cd examples/simple
./run.sh
```

The script will:
1. Build and install `structSimV1` to your local Maven repo
2. Create `/tmp/structsim-results` and `/tmp/structsim-simulator`
3. Run the simulation
4. Print `SummaryFile.txt` when done
