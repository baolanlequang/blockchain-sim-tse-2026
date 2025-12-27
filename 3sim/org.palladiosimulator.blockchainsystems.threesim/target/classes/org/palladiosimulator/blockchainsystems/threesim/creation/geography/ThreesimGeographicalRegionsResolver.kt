package org.palladiosimulator.blockchainsystems.threesim.creation.geography

import org.palladiosimulator.blockchainsystems.bscm.geographicalregions.GeographicalRegionsSpecification
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegion
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegions
import org.palladiosimulator.blockchainsystems.core.geography.GeographicalRegionsResolver
import org.palladiosimulator.blockchainsystems.threesim.creation.abstractions.NodeAllocationResolver

/**
 * Resolves geographical regions based on the provided geographical regions specification and node allocation resolver.
 *
 * @property geoRegionsSpec The geographical regions specification containing the region definitions.
 * @property nodeAllocationResolver The resolver to determine node allocations and their corresponding geographical regions.
 *
 * @author Davis Riedel
 */
class ThreesimGeographicalRegionsResolver(
  private val geoRegionsSpec: GeographicalRegionsSpecification,
  private val nodeAllocationResolver: NodeAllocationResolver
) : GeographicalRegionsResolver {
  private val geoRegions = GeographicalRegions(
    geoRegionsSpec.regions
      .map { region -> GeographicalRegion(region.regionName) }
      .toSet()
  )

  override fun resolveGeographicalRegions(): GeographicalRegions {
    // NOTE: This is cached in the constructor to avoid repeated computation.
    return geoRegions
  }

  override fun getGeographicalRegionOfNode(nodeId: String): GeographicalRegion {
    val regionName = nodeAllocationResolver
      .getNodeAllocation(nodeId)
      ?.geographicalRegion
      ?.regionName ?: throw IllegalArgumentException(
      "Node with ID '$nodeId' does not have a geographical region defined in the node allocation."
    )

    requireNotNull(geoRegions.getRegionByName(regionName)) {
      "Geographical region with name '$regionName' not found in the geographical regions specification."
    }

    return GeographicalRegion(regionName)
  }
}
