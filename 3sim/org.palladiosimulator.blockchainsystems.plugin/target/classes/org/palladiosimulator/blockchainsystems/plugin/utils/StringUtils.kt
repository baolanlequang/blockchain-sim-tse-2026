package org.palladiosimulator.blockchainsystems.plugin.utils

const val MINIMUM_PORT: Long = 1
const val MAXIMUM_PORT: Long = 65536

fun String.isPort(): Boolean {
  val nr = toLongOrNull() ?: return false
  return nr in MINIMUM_PORT..MAXIMUM_PORT
}