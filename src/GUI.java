import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame {
    public int mx = -100;
    public int my = -100;
    // set up
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int X = 16;
    public static final int Y = 9;
    public static final int SPACING = 4;
    public static final int BOMB_PERCENT = 10;

    Cell cells[][] = new Cell[X][Y];

    public GUI() {
        this.setTitle("Minesweeper");
        // this.setSize(1286, 829); //Original Vals 1280+6, 800+29
        this.setSize(X * WIDTH + 13, (Y + 1) * HEIGHT + 37);
        this.setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        Random rand = new Random();
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                cells[i][j] = new Cell();
                if (rand.nextInt(100) < BOMB_PERCENT) {
                    cells[i][j].mine = true;
                }
            }
        }
        Board board = new Board();
        this.setContentPane(board);
        Move move = new Move();
        this.addMouseMotionListener(move);
        Click click = new Click();
        this.addMouseListener(click);

    }

    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    int recx = SPACING + i * GUI.WIDTH;
                    int recy = SPACING + (j + 1) * GUI.HEIGHT;
                    int recw = GUI.WIDTH - 2 * SPACING;
                    int rech = GUI.HEIGHT - 2 * SPACING;
                    g.setColor(Color.gray);
                    if (cells[i][j].mine) {
                        g.setColor(Color.RED);
                    }
                    if (inCell(i, j))
                        g.setColor(Color.lightGray);
                    if (cells[i][j].flagged) {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect(recx, recy, recw, rech);
                }
            }
        }
    }

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            System.out.println("Dragged");
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mx = e.getX() - 6;
            my = e.getY() - 30;
            // System.out.println(mx + " " + my);
        }
    }

    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            System.out.println("Clicked");
            int[] cell = MouseOnCell();
            if (cell[0] != -1)
                cells[cell[0]][cell[1]].flagged = true;
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    public class Cell {
        boolean mine;
        int neighbours;
        boolean revealed;
        boolean flagged;

        public Cell() {
            mine = false;
            neighbours = 0;
            revealed = false;
            flagged = false;
        }
    }

    public int[] MouseOnCell() {
        int[] out = new int[2];
        out[0] = -1;
        out[1] = -1;
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (inCell(i, j)) {
                    out[0] = i;
                    out[1] = j;
                    return out;
                }
            }
        }
        return out;
    }

    public boolean inCell(int i, int j) {
        int recx = SPACING + i * GUI.WIDTH;
        int recy = SPACING + (j + 1) * GUI.HEIGHT;
        int recw = GUI.WIDTH - 2 * SPACING;
        int rech = GUI.HEIGHT - 2 * SPACING;
        return mx >= recx && mx <= recx + recw && my >= recy && my <= recy + rech;

    }
}