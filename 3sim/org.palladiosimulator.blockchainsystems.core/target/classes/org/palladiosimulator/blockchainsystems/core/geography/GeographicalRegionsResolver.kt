package org.palladiosimulator.blockchainsystems.core.geography

interface GeographicalRegionsResolver {
  fun resolveGeographicalRegions(): GeographicalRegions
  fun getGeographicalRegionOfNode(nodeId: String): GeographicalRegion
}