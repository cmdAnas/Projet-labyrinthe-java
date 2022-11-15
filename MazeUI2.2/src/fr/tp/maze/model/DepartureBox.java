package fr.tp.maze.model;

import java.io.PrintWriter;

public class DepartureBox extends MazeBox {

    public DepartureBox(Maze maze, int lineNo, int colNo) {
        super(maze, lineNo, colNo);
    }

    @Override
    public boolean isDeparture() {
        return true;
    }

    public final void writeCharTo(PrintWriter pw) {
        pw.print('D');
    }

    @Override
    public String toString() {
        return "DepartureBox{} " + super.toString();
    }
}
