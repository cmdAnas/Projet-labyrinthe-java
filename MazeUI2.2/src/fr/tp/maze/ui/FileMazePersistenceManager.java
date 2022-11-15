package fr.tp.maze.ui;

import fr.tp.maze.exception.MazeReadingException;
import fr.tp.maze.model.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class FileMazePersistenceManager implements MazePersistenceManager {

    private final char BOX_DEPARTURE = 'D';
    private final char BOX_ARRIVAL = 'A';
    private final char BOX_WALL = 'W';
    private final char BOX_EMPTY = 'E';

    private Component editor;

    private final FileNameExtensionFilter filter;

    public FileMazePersistenceManager() {
        this.editor = null;
        filter = new FileNameExtensionFilter("Maze files only (*.maze)", "maze");
    }

    public void setEditor(Component editor) {
        this.editor = editor;
    }

    @Override
    public MazeModel read(String mazeId)
            throws IOException {
        if (mazeId == null || mazeId.isEmpty()) {
            final JFileChooser chooser = new JFileChooser(); //This class enable us to open a file explorer for a more ergonomic design.
            chooser.setFileFilter(filter);
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);

            final int returnVal = chooser.showOpenDialog(editor);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = chooser.getSelectedFile();
                mazeId = file.getPath();
            } else {
                throw new IOException("No file selected!");
            }
        }

        return doRead(mazeId);
    }

    protected MazeModel doRead(final String mazeId)
            throws IOException {

        FileReader fr = null;
        BufferedReader br = null;
        int height = 10;
        int width = 10;
        MazeBox[][] boxes = new MazeBox[height][width];
        Maze maze = new Maze(height, width);
        try {
            fr = new FileReader(mazeId);
            br = new BufferedReader(fr);

            for (int lineNo = 0; lineNo < height; lineNo++) {
                String line = br.readLine();

                if (line == null) throw new MazeReadingException(mazeId, lineNo, "not enough lines");
                if (line.length() < width) throw new MazeReadingException(mazeId, lineNo, "line too short");
                if (line.length() > width) throw new MazeReadingException(mazeId, lineNo, "line too long");


                for (int colNo = 0; colNo < width; colNo++) {
                    switch (line.charAt(colNo)) {
                        case 'D':
                            maze.setBox(boxes[lineNo][colNo] = new DepartureBox(maze, lineNo, colNo));
                            break;
                        case 'A':
                            maze.setBox(boxes[lineNo][colNo] = new ArrivalBox(maze, lineNo, colNo));
                            break;
                        case 'W':
                            maze.setBox(boxes[lineNo][colNo] = new WallBox(maze, lineNo, colNo));
                            break;
                        case 'E':
                            maze.setBox(boxes[lineNo][colNo] = new EmptyBox(maze, lineNo, colNo));
                            break;
                        default:
                            throw new MazeReadingException(mazeId, lineNo, "unknown char'" + boxes[lineNo][colNo] + "'");

                    }
                }


            }
        } catch (MazeReadingException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("error class Maze, initFromTextFile: file not found \"" + mazeId + "\"");
        } catch (IOException e) {
            System.err.println("error class Maze, initFromTextFile: read error on file \"" + mazeId + "\"");
        } catch (Exception e) {
            System.err.println("Error:Unknown error.");
            e.printStackTrace(System.err);
        } finally {
            if (fr != null) try {
                fr.close();
            } catch (Exception e) {
            }
            ;
            if (br != null) try {
                br.close();
            } catch (Exception e) {
            }

        }


        return maze;

    }

    @Override
    public void persist(final MazeModel mazeModel)
            throws IOException {
        String mazeId = mazeModel.getId();

        if (mazeId == null || mazeId.isEmpty()) {
            mazeId = newMazeId();

            if (mazeId == null || mazeId.isEmpty()) {
                throw new IOException("No file path was choosen!");
            }

            mazeModel.setId(mazeId);
        }

        doPersist(mazeModel);
    }

    protected void doPersist(final MazeModel mazeModel)
            throws IOException {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(mazeModel.getId());
            for (int lineNo = 0; lineNo < mazeModel.getHeight(); lineNo++) {
                int colNo = 0;
                MazeBox mazeBox = (MazeBox) mazeModel.getMazeBox(lineNo, colNo);
                while (colNo < mazeModel.getWidth() - 1) {
                    mazeBox.writeCharTo(pw);
                    colNo++;
                }
                pw.println();
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error class Maze,saveToTextFile:file not found \"" + mazeModel.getId() + "\"");

        } catch (SecurityException e) {
            System.err.println("error class Maze,saveToextFile: secrit exception \"" + mazeModel.getId() + "\"");


        } catch (Exception ex) {
            System.err.println("Error : unknown error.");
            ex.printStackTrace(System.err);

        } finally {
            if (pw != null) try {
                pw.close();
            } catch (Exception e) {
            }
            ;
        }
    }

    @Override
    public boolean delete(MazeModel mazeModel)
            throws IOException {
        return new File(mazeModel.getId()).delete();
    }

    private String newMazeId() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        final int returnVal = chooser.showSaveDialog(editor);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File file = chooser.getSelectedFile();

            return file.getPath();
        }

        return null;
    }
}
