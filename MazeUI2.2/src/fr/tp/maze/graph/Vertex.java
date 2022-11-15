package fr.tp.maze.graph;

import java.util.Set;

public interface Vertex {

	Set<Vertex> getSuccessors();
	
	String getLabel();
}
