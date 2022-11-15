package fr.tp.maze.model;

import fr.tp.maze.graph.Vertex;

import java.io.PrintWriter;
import java.util.Set;

public abstract class MazeBox implements MazeBoxModel, Vertex {

    private int rowIndex;
    private int colIndex;

    private Maze maze;


    public MazeBox(Maze maze, int lineNo, int colNo) {
        this.rowIndex = lineNo;
        this.colIndex = colNo;
        this.maze = maze;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setEmpty() {
        maze.getEmptyBox(rowIndex, colIndex);
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public void setWall() {
        maze.getWallBox(rowIndex, colIndex);
    }

    @Override
    public boolean isDeparture() {
        return false;
    }

    @Override
    public void setDeparture() {
        maze.getDepartureBox(rowIndex, colIndex);
    }

    @Override
    public boolean isArrival() {
        return false;
    }

    @Override
    public void setArrival() {
        maze.getArrivalBox(rowIndex, colIndex);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    @Override
    public boolean belongsToShortestPath() {
        return this.maze.getShortestPath().getShortestPath(maze.getArrival(),
                maze.getArrival()).contains(this);
    }


    @Override
    public Set<Vertex> getSuccessors() {
        return maze.getSuccessors(this);
    }

    @Override
    public String getLabel() {
        return "(" + rowIndex + "," + colIndex + ")";
    }

    public  boolean isAccessible(){
        return true;
    }

    public abstract void writeCharTo(PrintWriter pw);

    @Override
    public String toString() {
        return "MazeBox{" +
                "rowIndex=" + rowIndex +
                ", colIndex=" + colIndex +
                '}';
    }
}
