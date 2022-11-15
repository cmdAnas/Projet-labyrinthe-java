package fr.tp.maze.model;

public class MazeFactoryImpl implements MazeFactory {
    @Override
    public MazeModel createMazeModel(int height, int width) {
        return new Maze(height, width);
    }
}
