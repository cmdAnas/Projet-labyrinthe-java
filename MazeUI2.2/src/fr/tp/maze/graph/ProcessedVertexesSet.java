package fr.tp.maze.graph;

public interface ProcessedVertexesSet {
	
	void add( Vertex vertex );
	
	boolean contains( Vertex vertex );
}
