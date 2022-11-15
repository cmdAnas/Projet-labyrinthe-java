package fr.tp.maze;

import fr.tp.maze.model.Maze;
import fr.tp.maze.model.MazeModel;
import fr.tp.maze.ui.FileMazePersistenceManager;
import fr.tp.maze.ui.MazeEditor;

public class Main {
    public static void main(String[] args) {
        final MazeModel maze = new Maze(10,10);
        FileMazePersistenceManager persistenceManager = new FileMazePersistenceManager();
        MazeEditor mazeEditor = new MazeEditor(maze,persistenceManager);
        persistenceManager.setEditor(mazeEditor);
    }
}
