package fr.tp.maze.model;

import java.io.PrintWriter;

public class WallBox extends MazeBox {

    public WallBox(Maze maze, int lineNo, int colNo) {
        super(maze, lineNo, colNo);
    }

    @Override
    public final void writeCharTo(PrintWriter pw) {
        pw.print('W');
    }

    @Override
    public boolean isAccessible() {
        return false;
    }

    @Override
    public boolean isWall() {
        return true;
    }


    @Override
    public String toString() {
        return "WallBox{} " + super.toString();
    }
}
