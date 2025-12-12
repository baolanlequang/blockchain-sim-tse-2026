package org.palladiosimulator.blockchainsystems.threesim.utils

import org.jgrapht.graph.AbstractBaseGraph

fun <V, E> AbstractBaseGraph<V, E>.addBidirectionalEdge(
  firstVertex: V,
  secondVertex: V,
  getElement: (fromVertex: V, toVertex: V) -> E
) {
  this.addEdge(firstVertex, secondVertex, getElement(firstVertex, secondVertex))
  this.addEdge(secondVertex, firstVertex, getElement(secondVertex, firstVertex))
}
