package org.palladiosimulator.blockchainsystems.trilemma;

import com.google.common.io.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainSystem;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystem.BlockchainsystemPackage;
import org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage;
import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalregionsPackage;
import org.palladiosimulator.blockchainsystems.bscm.linkallocation.LinkallocationPackage;
import org.palladiosimulator.blockchainsystems.bscm.nodeallocation.NodeallocationPackage;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.ConnectedSubgraphsNetworkTopology;
import org.palladiosimulator.blockchainsystems.bscm.p2pnetwork.P2pnetworkPackage;
import org.palladiosimulator.blockchainsystems.bscm.transactions.TransactionsPackage;

/**
 * Loads one reusable 3SIM model and applies one sampled design--operational pair
 * in memory.  The refined experiment therefore no longer needs a separately
 * materialized model folder for every CSV row.
 */
public class BlockchainSystemModelLoader {

    public BlockchainSystem load(String uri) {
        Path modelFile = Paths.get(uri).toAbsolutePath().normalize();
        if (!java.nio.file.Files.exists(modelFile)) {
            throw new IllegalArgumentException("Blockchain-system model does not exist: " + modelFile);
        }
        Path modelDirectory = modelFile.getParent();
        if (modelDirectory == null) {
            throw new IllegalArgumentException("Blockchain-system model must have a parent directory: " + modelFile);
        }
        String folderName = modelDirectory.getFileName().toString();
        String fileName = modelFile.getFileName().toString();
        String baseName = Files.getNameWithoutExtension(fileName);

        ResourceSet resourceSet = new ResourceSetImpl();
        XMIResourceFactoryImpl xmiFactory = new XMIResourceFactoryImpl();
        for (String ext : List.of(
                "blockchainsystem", "p2pnetwork", "bscmrepository",
                "blockchainsystemcomponentrepository", "nodeallocation",
                "geographicalregions", "linkallocation", "transactions")) {
            resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(ext, xmiFactory);
        }

        resourceSet.getPackageRegistry().put(BlockchainsystemPackage.eNS_URI, BlockchainsystemPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(P2pnetworkPackage.eNS_URI, P2pnetworkPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(NodeallocationPackage.eNS_URI, NodeallocationPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(
                BlockchainsystemComponentRepositoryPackage.eNS_URI,
                BlockchainsystemComponentRepositoryPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(LinkallocationPackage.eNS_URI, LinkallocationPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(TransactionsPackage.eNS_URI, TransactionsPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(GeographicalregionsPackage.eNS_URI, GeographicalregionsPackage.eINSTANCE);

        // The model may be executed from Eclipse, Maven/Tycho, a packaged jar, or
        // a local checkout.  Resolve the logical platform:/plugin URIs used by
        // EMF to the *actual directory supplied by the runner* instead of
        // assuming a particular current working directory.
        map(resourceSet, folderName, fileName, modelDirectory.resolve(fileName));

        // 3SIM repositories exist in two compatible filename conventions:
        // Net.bscmrepository and Net.blockchainsystemcomponentrepository.  The
        // recovered legacy base used by this study uses the latter.  Resolve the
        // actual file instead of forcing one convention, because EMF hrefs retain
        // the original filename and must be mapped exactly.
        List<String> companionFiles = new ArrayList<>();
        for (String ext : List.of(
                "p2pnetwork", "nodeallocation", "geographicalregions",
                "linkallocation", "transactions")) {
            companionFiles.add(baseName + "." + ext);
        }
        Path repositoryFile = firstExisting(
                modelDirectory.resolve(baseName + ".bscmrepository"),
                modelDirectory.resolve(baseName + ".blockchainsystemcomponentrepository"));
        if (repositoryFile == null) {
            throw new IllegalArgumentException(
                    "Required component-repository model is missing. Expected either "
                            + modelDirectory.resolve(baseName + ".bscmrepository")
                            + " or "
                            + modelDirectory.resolve(baseName + ".blockchainsystemcomponentrepository"));
        }
        companionFiles.add(repositoryFile.getFileName().toString());

        for (String relative : companionFiles) {
            Path actual = modelDirectory.resolve(relative);
            if (!java.nio.file.Files.exists(actual)) {
                throw new IllegalArgumentException("Required companion model file does not exist: " + actual);
            }
            map(resourceSet, folderName, relative, actual);
        }

        resourceSet.getResource(createRelativePluginURI(folderName, fileName), true);
        for (String relative : companionFiles) {
            resourceSet.getResource(createRelativePluginURI(folderName, relative), true);
        }

        ArrayList<Resource> currentResources;
        do {
            currentResources = new ArrayList<>(resourceSet.getResources());
            for (Resource r : currentResources) {
                EcoreUtil.resolveAll(r);
            }
        } while (currentResources.size() != resourceSet.getResources().size());

        return (BlockchainSystem) currentResources.get(0).getContents().get(0);
    }

    /** Load the base model and translate one refined experimental configuration. */
    public BlockchainSystem load(String uri, Map<String, String> configuration) {
        BlockchainSystem system = load(uri);
        applyRefinedConfiguration(system, configuration);
        return system;
    }

    private void applyRefinedConfiguration(BlockchainSystem system, Map<String, String> c) {
        final int connectionCount = requiredInt(c, "connection_count");
        final int bciSeconds = requiredInt(c, "block_creation_interval");
        final double mbsMb = requiredDouble(c, "maximum_block_size");
        final int nv = requiredInt(c, "validating_node_count");
        final double hNode = requiredDouble(c, "node_bandwidth_heterogeneity");
        final double hLink = requiredDouble(c, "link_bandwidth_heterogeneity");
        final double hHash = requiredDouble(c, "hashing_power_concentration");
        final boolean hasSampledAttackerFraction = c.containsKey("fraction_of_attackers")
                && c.get("fraction_of_attackers") != null
                && !c.get("fraction_of_attackers").isBlank();
        final double fA = hasSampledAttackerFraction
                ? requiredDouble(c, "fraction_of_attackers")
                : Double.NaN;
        final double lambdaTx = requiredDouble(c, "transaction_arrival_rate");

        requireRange("connection_count", connectionCount, 1, 8);
        requireRange("block_creation_interval", bciSeconds, 60.0, 1200.0);
        requireRange("maximum_block_size", mbsMb, 0.25, 8.0);
        requireRange("validating_node_count", nv, 20, 1000);
        requireRange("node_bandwidth_heterogeneity", hNode, 0.0, 0.50);
        requireRange("link_bandwidth_heterogeneity", hLink, 0.0, 0.40);
        requireRange("hashing_power_concentration", hHash, 0.0, 0.50);
        if (hasSampledAttackerFraction) {
            requireRange("fraction_of_attackers", fA, 0.0, 0.25);
        } else if (!c.containsKey("number_of_attackers") || c.get("number_of_attackers").isBlank()) {
            throw new IllegalArgumentException(
                    "Refined input requires fraction_of_attackers; number_of_attackers is accepted only as a pilot-compatibility fallback.");
        }
        if (!(lambdaTx > 0.0) || !Double.isFinite(lambdaTx)) {
            throw new IllegalArgumentException("transaction_arrival_rate must be finite and > 0; got " + lambdaTx);
        }
        if (2 * connectionCount > nv - 1) {
            throw new IllegalArgumentException(
                    "Infeasible topology: 2C=" + (2 * connectionCount) + " > N_V-1=" + (nv - 1));
        }

        // System-level design values. BCI is supplied in seconds by sampling;
        // the current 3SIM metamodel stores event durations in milliseconds.
        system.getSpecification().setMeanBlockTime(bciSeconds * 1000.0);
        system.getSpecification().setMaxBlockSize((int) Math.round(mbsMb * 1_000_000.0));
        system.getSpecification().setHashRateConcentration(hHash);

        int derivedAttackers;
        if (hasSampledAttackerFraction) {
            derivedAttackers = deriveAttackerCount(nv, fA);
            if (c.containsKey("number_of_attackers") && !c.get("number_of_attackers").isBlank()) {
                int provided = requiredInt(c, "number_of_attackers");
                if (provided != derivedAttackers) {
                    throw new IllegalArgumentException(
                            "number_of_attackers=" + provided + " disagrees with f_A,N_V translation=" + derivedAttackers);
                }
            }
        } else {
            // Compatibility for the originally exported 24-case pilot CSV, which
            // retained only the already-derived integer attacker count.  Final
            // production inputs should always carry the sampled f_A as well.
            derivedAttackers = requiredInt(c, "number_of_attackers");
            // Pilot-compatibility fallback for old CSVs that retained only N_A.
            // Use the same half-up translation as the sampled f_A path when
            // determining the largest allowed count. Using floor(0.25*N_V)
            // would incorrectly reject legitimate boundary cases (e.g. N_V=22,
            // f_A=0.25 -> round(5.5)=6 attackers).
            int maxAttackers = deriveAttackerCount(nv, 0.25);
            if (derivedAttackers < 0 || derivedAttackers > maxAttackers) {
                throw new IllegalArgumentException(
                        "number_of_attackers=" + derivedAttackers + " outside pilot-compatible [0," + maxAttackers + "] for N_V=" + nv);
            }
        }
        system.getSpecification().setNumberOfAttacker(derivedAttackers);

        if (!(system.getNetwork().getTopology() instanceof ConnectedSubgraphsNetworkTopology topology)) {
            throw new IllegalArgumentException("Refined experiment requires ConnectedSubgraphsNetworkTopology.");
        }
        if (topology.getSubgraphs().size() != 1) {
            throw new IllegalArgumentException(
                    "Refined experiment requires exactly one subgraph; found " + topology.getSubgraphs().size());
        }
        var subgraph = topology.getSubgraphs().get(0);
        if (subgraph.getNodeTemplates().isEmpty()) {
            throw new IllegalArgumentException("Base model contains no node template.");
        }

        // Use one homogeneous non-crashing execution template for all N_V validators.
        // Resource heterogeneity is introduced explicitly by the normalized H
        // parameters rather than by mixing the legacy crash/no-crash templates.
        // Select by the allocation's model name instead of relying on template
        // ordering; this makes the translation robust to harmless model reordering.
        var executionTemplate = subgraph.getNodeTemplates().stream()
                .filter(t -> t.getAllocation() != null
                        && "NoCrash".equalsIgnoreCase(t.getAllocation().getEntityName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Refined experiment requires a non-crashing validator template named NoCrash."));
        for (var template : subgraph.getNodeTemplates()) {
            template.setNumberOfNodeOccurences(template == executionTemplate ? nv : 0);
        }

        // Both metamodel fields carry the single study parameter C.  The revised
        // topology factory interprets them as C initiated and C accepted peers.
        subgraph.getConnectivitySpecification().setNumberOfInbound(connectionCount);
        subgraph.getConnectivitySpecification().setNumberOfOutBound(connectionCount);

        // B_total = 2 * B_endpoint * N_V * C.  The per-endpoint baseline is a
        // fixed simulator input and must be stated explicitly in the pilot config.
        double endpointMbps = requiredDouble(c, "baselineBandwidthPerEndpointMbps");
        if (!(endpointMbps > 0.0) || !Double.isFinite(endpointMbps)) {
            throw new IllegalArgumentException("baselineBandwidthPerEndpointMbps must be finite and > 0.");
        }
        double totalBudgetMbps = 2.0 * endpointMbps * nv * connectionCount;
        var bw = subgraph.getLinkAllocation().getBandwidthSpecification();
        bw.setBandwidth(totalBudgetMbps);
        bw.setHeterogeneityNodeTarget(hNode);
        bw.setHeterogeneityLinkTarget(hLink);

        // Keep any connectivity-referenced link allocations internally consistent
        // even though the refined network factory uses the common subgraph allocation.
        var inboundAllocation = subgraph.getConnectivitySpecification().getInBoundLinkAllocationSpecification();
        if (inboundAllocation != null && inboundAllocation.getBandwidthSpecification() != null) {
            inboundAllocation.getBandwidthSpecification().setBandwidth(totalBudgetMbps);
            inboundAllocation.getBandwidthSpecification().setHeterogeneityNodeTarget(hNode);
            inboundAllocation.getBandwidthSpecification().setHeterogeneityLinkTarget(hLink);
        }
        var outboundAllocation = subgraph.getConnectivitySpecification().getOutBoundLinkAllocationSpecification();
        if (outboundAllocation != null && outboundAllocation.getBandwidthSpecification() != null) {
            outboundAllocation.getBandwidthSpecification().setBandwidth(totalBudgetMbps);
            outboundAllocation.getBandwidthSpecification().setHeterogeneityNodeTarget(hNode);
            outboundAllocation.getBandwidthSpecification().setHeterogeneityLinkTarget(hLink);
        }

        // lambda_tx is in transactions/second; the current metamodel stores the
        // mean interarrival time in milliseconds.
        system.getTransactionsSpecification().setMeanTransactionCreationInterval(1000.0 / lambdaTx);

        // Current study setting: fixed 500-byte transactions.  Preserve the
        // existing fee and amount values/distribution while normalizing size.
        int transactionSizeBytes = Integer.parseInt(c.getOrDefault("transactionSizeBytes", "500"));
        if (transactionSizeBytes <= 0) {
            throw new IllegalArgumentException("transactionSizeBytes must be > 0.");
        }
        system.getTransactionsSpecification()
                .getTransactionPropertiesSpecification()
                .getValues()
                .forEach(v -> v.setSize(transactionSizeBytes));

        int confirmationDepth = Integer.parseInt(c.getOrDefault("confirmationDepthBlocks", "6"));
        if (confirmationDepth < 1) {
            throw new IllegalArgumentException("confirmationDepthBlocks must be >= 1.");
        }
        system.getSpecification().setNumOfRequiredSecurityConfirmations(confirmationDepth);
    }

    private static int deriveAttackerCount(int nv, double fA) {
        if (fA == 0.0) return 0;
        return Math.max(1, (int) Math.floor(fA * nv + 0.5));
    }

    private static int requiredInt(Map<String, String> c, String key) {
        String raw = required(c, key);
        try {
            double value = Double.parseDouble(raw);
            int integer = (int) Math.rint(value);
            if (Math.abs(value - integer) > 1e-9) {
                throw new NumberFormatException("not an integer");
            }
            return integer;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer for " + key + ": " + raw, e);
        }
    }

    private static double requiredDouble(Map<String, String> c, String key) {
        String raw = required(c, key);
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number for " + key + ": " + raw, e);
        }
    }

    private static String required(Map<String, String> c, String key) {
        String raw = c.get(key);
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Missing required configuration value: " + key);
        }
        return raw.trim();
    }

    private static void requireRange(String name, double value, double lo, double hi) {
        if (!Double.isFinite(value) || value < lo || value > hi) {
            throw new IllegalArgumentException(name + "=" + value + " outside [" + lo + ", " + hi + "]");
        }
    }

    private static Path firstExisting(Path... candidates) {
        for (Path candidate : candidates) {
            if (java.nio.file.Files.exists(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    private static void map(ResourceSet resourceSet, String folderName, String relativePath, Path actualFile) {
        resourceSet.getURIConverter().getURIMap().put(
                createRelativePluginURIStatic(folderName, relativePath),
                URI.createFileURI(actualFile.toAbsolutePath().normalize().toString()));
    }

    private URI createRelativePluginURI(String folder, String relativePath) {
        return createRelativePluginURIStatic(folder, relativePath);
    }

    private URI createFilePluginURI(String folder, String relativePath) {
        return createFilePluginURIStatic(folder, relativePath);
    }

    private static URI createRelativePluginURIStatic(String folder, String relativePath) {
        String path = Paths.get(
                "org.palladiosimulator.blockchainsystems.trilemma/testmodels/" + folder,
                relativePath).toString();
        return URI.createPlatformPluginURI(path, false);
    }

    private static URI createFilePluginURIStatic(String folder, String relativePath) {
        String path = Paths.get(
                "org.palladiosimulator.blockchainsystems.trilemma/testmodels/" + folder,
                relativePath).toString();
        return URI.createURI(path);
    }
}
