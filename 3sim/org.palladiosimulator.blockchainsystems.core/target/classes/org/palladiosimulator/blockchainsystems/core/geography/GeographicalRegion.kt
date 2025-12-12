package org.palladiosimulator.blockchainsystems.core.geography

/**
 * Represents a geographical region.
 *
 * @property region The name of the geographical region.
 *
 * @author Davis Riedel
 */
data class GeographicalRegion(val region: String)

// NOTE: Data class ensures that a [GeographicalRegion] object is uniquely identified by the `region` property.