/**
 * Copyright (c) 2002-2013 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.docgen.cookbook

import org.neo4j.cypher.docgen.ArticleTest
import org.neo4j.cypher.{ExecutionResult, StatisticsChecker}
import org.neo4j.visualization.graphviz.GraphStyle
import org.neo4j.visualization.graphviz.AsciiDocSimpleStyle

class IngraphIndexTest extends ArticleTest with StatisticsChecker {
   val graphDescription = List("parent RANK A", "parent RANK B", "parent RANK C")
  val section = "cookbook"
  val title = "Linked List"

  override protected def getGraphvizStyle: GraphStyle = 
    AsciiDocSimpleStyle.withAutomaticRelationshipTypeColors()

  override def assert(name: String, result: ExecutionResult) {
    name match {
      case "create" =>
        assertStats(result, nodesCreated = 1, relationshipsCreated = 1, propertiesSet = 0)
        assert(result.toList.size === 0)
    }
  }

  override val properties: Map[String, Map[String, Any]] = Map(
    "A" -> Map("score" -> 60),
    "B" -> Map("score" -> 20),
    "C" -> Map("score" -> 70)
  )


  def text = """
Linked Lists
============

###graph-image graph [rankdir=RL]###

To initialize an empty linked list, we simply create an empty node, and make it link to itself.

###no-results empty-graph assertion=create
start n=node:node_auto_index(name = "parent") 
match n-[r:rank]->rn 
where rn.score <= rank_node.score 
return rank_node,count(*) as pos###


"""
}
