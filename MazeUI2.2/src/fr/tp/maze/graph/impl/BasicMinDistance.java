package fr.tp.maze.graph.impl;

import fr.tp.maze.graph.MinDistance;
import fr.tp.maze.graph.ProcessedVertexesSet;
import fr.tp.maze.graph.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BasicMinDistance implements MinDistance {

	private final Map<Vertex, Integer> minDistances;

	public BasicMinDistance() {
		minDistances = new HashMap<>();
	}

	@Override
	public void setMinDistance(	Vertex vertex,
								int distance ) {
		minDistances.put( vertex, distance );
	}

	@Override
	public int getMinDistance(Vertex vertex) {
		return minDistances.get( vertex );
	}

	@Override
	public Vertex getMinDistanceVertex(	ProcessedVertexesSet processedVertexes,
										Set<Vertex> vertexes) {
		int minDistance = Integer.MAX_VALUE;
		Vertex minDistVertex = null;

		for ( final Vertex vertex : vertexes ) {
			if ( !processedVertexes.contains( vertex ) ) {
				final int minDistanceVertex = getMinDistance( vertex );

				if ( minDistanceVertex < minDistance ) {
					minDistance = minDistanceVertex;
					minDistVertex = vertex;
				}
			}
		}

		return minDistVertex;
	}
}
