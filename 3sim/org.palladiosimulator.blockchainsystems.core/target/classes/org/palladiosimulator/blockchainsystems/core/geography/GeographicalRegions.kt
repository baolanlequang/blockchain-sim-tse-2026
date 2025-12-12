package org.palladiosimulator.blockchainsystems.core.geography

/**
 * Stores all geographical regions of a blockchain system.
 *
 * @property regions the set of geographical regions available
 *
 * @author Davis Riedel
 */
data class GeographicalRegions(
  // NOTE: Since [GeographicalRegion] is a data class, the `Set` can only contain unique regions (with unique names).
  private val regions: Set<GeographicalRegion>
) {
  /**
   * Returns the geographical region with the specified name.
   *
   * @param regionName the name of the geographical region to retrieve
   * @return the [GeographicalRegion] with the specified name, or null if not found
   */
  fun getRegionByName(regionName: String): GeographicalRegion? {
    return regions.find { it.region == regionName }
  }

  fun getNumberOfRegions(): Int {
    return regions.size
  }
}