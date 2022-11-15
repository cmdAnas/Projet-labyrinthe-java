package fr.tp.maze.model;

import java.io.PrintWriter;

public class ArrivalBox extends MazeBox {

    public ArrivalBox(Maze maze, int lineNo, int colNo) {
        super(maze, lineNo, colNo);
    }


    @Override
    public boolean isArrival() {
        return true;
    }

    public final void writeCharTo(PrintWriter pw) {
        pw.print('A');
    }

    @Override
    public String toString() {
        return "ArrivalBox{} " + super.toString();
    }
}