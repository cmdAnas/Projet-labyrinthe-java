package fr.tp.maze.model;

import fr.tp.maze.graph.*;
import fr.tp.maze.graph.impl.BasicMinDistance;
import fr.tp.maze.graph.impl.BasicShortestPaths;
import fr.tp.maze.graph.impl.BasicVertexesSet;

import java.util.*;

public class Maze implements MazeModel, Graph, Distance {

    private String id;
    public int width;
    public int height;
    private MazeFactory mazeFactory;
    private Set<ModelObserver> observers;
    public MazeBox[][] boxes;

    private ShortestPaths shortestPath;
    private DepartureBox departure;
    private ArrivalBox arrival;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.observers = new HashSet<>();
        mazeFactory = new MazeFactoryImpl();
        this.shortestPath = new BasicShortestPaths();

        boxes = new MazeBox[this.height][this.width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                boxes[i][j] = new EmptyBox(this, i, j);
            }
        }

    }


    public void getDepartureBox(int rowIndex, int colIndex) {
        this.departure = new DepartureBox(this, rowIndex, colIndex);
        this.boxes[rowIndex][colIndex] = departure;
        notifyObservers();
    }

    public void getArrivalBox(int rowIndex, int colIndex) {
        this.arrival = new ArrivalBox(this, rowIndex, colIndex);
        this.boxes[rowIndex][colIndex] = arrival;
        notifyObservers();
    }

    public void getEmptyBox(int rowIndex, int colIndex) {
        this.boxes[rowIndex][colIndex] = new EmptyBox(this, rowIndex, colIndex);
        notifyObservers();
    }

    public void getWallBox(int rowIndex, int colIndex) {
        this.boxes[rowIndex][colIndex] = new WallBox(this, rowIndex, colIndex);

        notifyObservers();
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public ArrivalBox getArrival() {
        return arrival;
    }

    public DepartureBox getDeparture() {
        return departure;
    }

    public void setBox(MazeBox box) {
        boxes[box.getRowIndex()][box.getColIndex()] = box;
    }

    @Override
    public MazeBoxModel getMazeBox(int rowIndex, int colIndex) {
        return boxes[rowIndex][colIndex];
    }

    @Override
    public int getNumberOfBoxes() {
        return height * width;
    }

    @Override
    public void clearMaze() {
        boxes = new EmptyBox[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boxes[i][j] = new EmptyBox(this, height, width);
            }
        }
        notifyObservers();
    }


    @Override
    public void clearShortestPath() {
        this.shortestPath = new BasicShortestPaths();
        notifyObservers();
    }

    public ShortestPaths getShortestPath() {
        return shortestPath;
    }


    @Override
    public boolean solve() {
        if (departure != null && arrival != null) {
            this.shortestPath = Dijkstra.findShortestPaths(
                    this,
                    departure,
                    arrival,
                    new BasicVertexesSet(),
                    new BasicMinDistance(),
                    this);
            notifyObservers();
            return !shortestPath.getShortestPath(departure, arrival).isEmpty();
        }
        return false;

    }

    @Override
    public List<String> validate() {
        notifyObservers();
        return new ArrayList<>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String mazeId) {
        this.id = mazeId;
    }

    @Override
    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    @Override
    public boolean removeObserver(ModelObserver observer) {
        return observers.remove(observer);
    }

    protected void notifyObservers() {
        for (final ModelObserver observer : observers) {
            observer.modelStateChanged();
        }
    }

    @Override
    public MazeFactory getMazeFactory() {
        return mazeFactory;
    }

    @Override
    public Set<Vertex> getVertexes() {
        Set<Vertex> set = new HashSet<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                set.add(boxes[i][j]);
            }
        }
        notifyObservers();
        return set;
    }

    @Override
    public Vertex getVertex(String label) {
    	for(Vertex vertex : getVertexes()) {
    		if(vertex.getLabel().equals(label)) {
    			return vertex;
    		}
    	}
        return null;
    }

    @Override
    public Set<Vertex> getSuccessors(Vertex vertex) {
        final Set<Vertex> successors = new HashSet<>();
        MazeBox box = (MazeBox) vertex;
        final int line = box.getRowIndex();
        final int column = box.getColIndex();
        if (line < height && column < width) {
            if (line > 0) {
                MazeBox neighbor = boxes[line - 1][column];
                if (neighbor.isAccessible()) successors.add(neighbor);

            }
           if (line < height - 1) {
                MazeBox neighbor = boxes[line + 1][column];
                if (neighbor.isAccessible()) successors.add(neighbor);

            }
            if (column > 0) {
                MazeBox neighbor = boxes[line][column - 1];
                if (neighbor.isAccessible()) successors.add(neighbor);
            }
            if (column < width - 1) {
                MazeBox neighbor = boxes[line][column + 1];
                if (neighbor.isAccessible()) successors.add(neighbor);
            }
        }

        return successors;
    }


    @Override
    public int getDistance(Vertex vertex1, Vertex vertex2) {
        if (vertex1.getSuccessors().contains(vertex2)) {
            notifyObservers();
            return 1;
        } else {
            notifyObservers();
            return 0;
        }
    }

}
