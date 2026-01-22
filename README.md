# Using Simulator

### Prerequisites

Make sure the following prerequisites are installed:

- Eclipse 2025-06 RAP
- Palladio Nightly Plugin
- Java 17 or higher

### Installation
1. Open Eclipse, select Help > Install New Software... > Add.. > Archive ... > 3sim > releng > org.palladiosimulator.blockchainsystems.updatesite > target > org.palladiosimulator.blockchainsystems.updatesite-0.0.1-SNAPSHOT.zip

2. Install the following features from the 3SIM update site:
  - `Kotlin Dependencies`
  - `Blockchain Systems Component Model (BSCM)`
  - `Blockchain Simulator Core`
  - `3SIM`

3. Open Eclipse, select Help > Install New Software...> Add..:
  - Input `https://updatesite.mdsd.tools/library-standaloneinitialization/nightly/` into Location and install all supported libraries

4. Restart Eclipse and import `org.palladiosimulator.blockchainsystems.trilemma` project in `trilemma` folder.

### Usage
1. Edit `configuration.json` to change the configuation of the simulator in `testmodels` folder
2. Run `TrilemmaSimulator` as Java Application
3. Depending the blockchain system file path is defined at `blockchainSystemModelFilePath`, the result will be written as the corresponding json file.


#### What to change when the configuration of parameters changes

1. Update the following CSV file containing the configuration of parameters:
- optimized_deterministic_lhs_configurations.csv
2. Update Bach runner validation in TrilemmaSimulator.java. Locate validateCsvColumns(...) and update the required list. This is to prevent simulations with missing or inconsistent parameters.
3. Update model override logic
- In BlockchainSystemModelLoader, in ApplyConfigurationOverrides(), add or remove parameters as required.
