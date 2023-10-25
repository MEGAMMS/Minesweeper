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
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    public static final int X = 30;
    public static final int Y = 20;
    public static final int SPACING = 2;
    public static final int BOMB_PERCENT = 10;
    Image flag, bomb;
    Cell cells[][] = new Cell[X][Y];

    public GUI() {
        this.setTitle("Minesweeper");
        // this.setSize(1286, 829); //Original Vals 1280+6, 800+29
        this.setSize(X * WIDTH + 13, (Y + 1) * HEIGHT + 37);
        this.setBackground(Color.DARK_GRAY);
        this.setResizable(false);
        getImages();
        Random rand = new Random();

        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                cells[i][j] = new Cell();
                if (rand.nextInt(100) < BOMB_PERCENT) {
                    cells[i][j].mine = true;

                }
            }
        }
        countNeighbours();
        Board board = new Board();
        this.setContentPane(board);
        Move move = new Move();
        this.addMouseMotionListener(move);
        Click click = new Click();
        this.addMouseListener(click);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
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
                    if (cells[i][j].revealed) {
                        g.setColor(Color.lightGray);
                    }
                    if (inCell(i, j)) {
                        g.setColor(Color.lightGray);
                    }
                    g.fillRect(recx, recy, recw, rech);
                    if (cells[i][j].revealed) {
                        if (cells[i][j].neighbours == 0)
                            continue;

                        // g.setColor(ColorOfNum(cells[i][j].neighbours));
                        g.setColor(Color.black);
                        g.setFont(new Font("Tahoma", Font.BOLD, (GUI.HEIGHT * 3) / 5));
                        g.drawString(Integer.toString(cells[i][j].neighbours), recx + GUI.WIDTH / 5,
                                recy + (31 * GUI.HEIGHT) / 50);
                    }
                    if (cells[i][j].mine) {
                        g.drawImage(bomb, recx, recy, null);
                    }
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
            Pair point = MouseOnCell();
            if (point.x != -1) {
                reveal(point);
                System.out.println(cells[point.x][point.y].neighbours);

            }

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

    public Pair MouseOnCell() {
        Pair out = new Pair(-1, -1);
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                if (inCell(i, j)) {
                    out.x = i;
                    out.y = j;
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

    public void countNeighbours() {
        int[] d = { 0, 1, -1 };
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                int n = 0;
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (l == 0 && k == 0)
                            continue;
                        int nx = i + d[k];
                        int ny = j + d[l];
                        if (nx < 0 || ny < 0 || nx >= X || ny >= Y)
                            continue;
                        n += cells[i + d[k]][j + d[l]].mine ? 1 : 0;
                    }
                }
                cells[i][j].neighbours = n;
            }
        }
    }

    public void reveal(Pair cell) {
        if (cells[cell.x][cell.y].revealed || cells[cell.x][cell.y].mine)
            return;
        cells[cell.x][cell.y].revealed = true;
        if (cells[cell.x][cell.y].neighbours != 0)
            return;
        int[] d = { 0, 1, -1 };
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (l == 0 && k == 0)
                    continue;
                int nx = cell.x + d[k];
                int ny = cell.y + d[l];
                if (nx < 0 || ny < 0 || nx >= X || ny >= Y)
                    continue;
                reveal(new Pair(nx, ny));
            }
        }
        return;
    }

    void getImages() {
        flag = new ImageIcon("src\\Images\\red-flag.png").getImage();
        flag = flag.getScaledInstance((WIDTH * 9 / 10), (HEIGHT * 9 / 10), Image.SCALE_SMOOTH);
        bomb = new ImageIcon("src\\Images\\Bomb.png").getImage();
        bomb = bomb.getScaledInstance((WIDTH * 9 / 10), (HEIGHT * 9 / 10), Image.SCALE_SMOOTH);
    }

    public Color ColorOfNum(int num) {
        switch (num) {
            case 1:
                return Color.blue;
            case 2:
                return Color.green;
            case 3:
                return Color.red;
            case 4:
                return Color.magenta;
            case 5:
                return Color.orange;
            case 6:
                return Color.YELLOW;
            case 7:
                return Color.cyan;
            case 8:
                return Color.BLACK;
        }
        return Color.black;
    }

    public class Pair {
        int x = 0;
        int y = 0;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}