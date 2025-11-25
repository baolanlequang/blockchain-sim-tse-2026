package org.palladiosimulator.blockchainsystems.plugin;

/**
 * This class contains all attributes used in the plugin.
 *
 * @author Yannik Sproll, Davis Riedel
 * @implNote This class is not meant to be instantiated.
 */
public final class Attributes {

    private Attributes() {
    } // No instances of this class

    public static class ArchitecturalModels {
        public static final String BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE = "BlockchainSystemModelFilePath";
        public static final String BLOCKCHAIN_SYSTEM_MODEL_FILE_PATH_ATTRIBUTE_DEFAULT = "";

        public static final String SIMULATION_RESULT_FILE_DIRECTORY = "SimulationResultFileDirectory";
        public static final String SIMULATION_RESULT_FILE_DIRECTORY_DEFAULT = "";
    }

    public static class SimulationType {
        public static final String SIMULATION_TYPE_ATTRIBUTE = "SimulationType";
        public static final String SIMULATION_TYPE_ATTRIBUTE_DEFAULT =
                org.palladiosimulator.blockchainsystems.core.simulation.SimulationType.Single.toString();

        public static final String NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS = "NumberOfMonteCarloSimulationRounds";
        public static final String NUMBER_OF_MONTE_CARLO_SIMULATION_ROUNDS_DEFAULT = "10000";
    }

    public static class SimulationTermination {
        public static final String MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE = "MaxBlockchainLength";
        public static final String MAX_BLOCKCHAIN_LENGTH_ATTRIBUTE_DEFAULT = "100";

        public static final String REQUIRED_NUMBER_OF_BLOCKS_IN_ADVANCE_ATTRIBUTE = "RequiredNumberOfBlocksInAdvance";
        public static final String REQUIRED_NUMBER_OF_BLOCKS_IN_ADVANCE_ATTRIBUTE_DEFAULT = "5";
    }

    public static class Logging {
        public static final String IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE = "IsConsoleLoggingEnabled";
        public static final boolean IS_CONSOLE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT = false;

        public static final String IS_FILE_LOGGING_ENABLED_ATTRIBUTE = "IsFileLoggingEnabled";
        public static final boolean IS_FILE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT = false;

        public static final String LOG_FILE_PATH_ATTRIBUTE = "LogFilePath";
        public static final String LOG_FILE_PATH_ATTRIBUTE_DEFAULT = "";

        public static final String IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE = "IsDatabaseLoggingEnabled";
        public static final boolean IS_DATABASE_LOGGING_ENABLED_ATTRIBUTE_DEFAULT = false;

        public static final String DATABASE_SERVER_ATTRIBUTE = "DatabaseServer";
        public static final String DATABASE_SERVER_ATTRIBUTE_DEFAULT = "";

        public static final String DATABASE_NAME_ATTRIBUTE = "DatabaseName";
        public static final String DATABASE_NAME_ATTRIBUTE_DEFAULT = "";

        public static final String DATABASE_PORT_ATTRIBUTE = "DatabasePort";
        public static final String DATABASE_PORT_ATTRIBUTE_DEFAULT = "";

        public static final String DATABASE_USERNAME_ATTRIBUTE = "DatabaseUsername";
        public static final String DATABASE_USERNAME_ATTRIBUTE_DEFAULT = "";

        public static final String DATABASE_PASSWORD_ATTRIBUTE = "DatabasePassword";
        public static final String DATABASE_PASSWORD_ATTRIBUTE_DEFAULT = "";
    }
}
