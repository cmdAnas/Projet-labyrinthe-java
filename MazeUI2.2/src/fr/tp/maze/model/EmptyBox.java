package fr.tp.maze.model;

import java.io.PrintWriter;

public class EmptyBox extends MazeBox {

    public EmptyBox(Maze maze, int lineNo, int colNo) {
        super(maze, lineNo, colNo);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    public final void writeCharTo(PrintWriter pw) {
        pw.print('E');
    }

    @Override
    public String toString() {
        return "EmptyBox{} " + super.toString();
    }
}
