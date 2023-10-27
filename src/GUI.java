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
    public static final int SPACING = 2;
    public static final int BOMB_PERCENT = 10;
    Image flag, bomb;
    Cell cells[][] = new Cell[X][Y];

    public GUI() {
        this.setTitle("Minesweeper");
        this.setSize(X * WIDTH + 13, (Y + 1) * HEIGHT + 37);
        this.setBackground(Color.decode("#27374D"));
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
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    Cell cell = cells[i][j];
                    int recx = SPACING + i * GUI.WIDTH;
                    int recy = SPACING + (j + 1) * GUI.HEIGHT;
                    int recw = GUI.WIDTH - 2 * SPACING;
                    int rech = GUI.HEIGHT - 2 * SPACING;
                    g.setColor(Color.decode("#526D82"));

                    if (cell.revealed) {
                        g.setColor(Color.lightGray);
                    }
                    if (inCell(i, j)) {
                        g.setColor(Color.lightGray);
                    }
                    if (cell.mine && cell.revealed) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(recx, recy, recw, rech);
                    g.drawImage(flag, 0, 0, null);
                    paintImage(g, cell, recx, recy);
                    paintTheNumber(g, cell, recx, recy);

                }
            }
        }

        private void paintImage(Graphics g, GUI.Cell cell, int recx, int recy) {
            if (cell.flagged) {
                g.drawImage(flag, recx + (GUI.WIDTH * 1) / 10, recy + (GUI.HEIGHT * 1) / 10, null);
            }
            if (cell.mine && cell.revealed) {
                g.drawImage(bomb, recx + (GUI.WIDTH * 1) / 20, recy + (GUI.HEIGHT * 1) / 20, null);
            }
        }

        private void paintTheNumber(Graphics g, GUI.Cell cell, int recx, int recy) {
            if (cell.revealed) {
                if (cell.neighbours == 0 || cell.mine)
                    return;

                g.setColor(ColorOfNum(cell.neighbours));
                // g.setColor(Color.black);
                g.setFont(new Font("Tahoma", Font.BOLD, (GUI.HEIGHT * 3) / 5));
                g.drawString(Integer.toString(cell.neighbours), recx + GUI.WIDTH / 5,
                        recy + (31 * GUI.HEIGHT) / 50);
            }
        }
    }

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            mx = e.getX() - 6;
            my = e.getY() - 30;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mx = e.getX() - 6;
            my = e.getY() - 30;
        }
    }

    public class Click implements MouseListener {
        Pair point = new Pair(-1, -1);

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        private void cellClicked(MouseEvent e, GUI.Cell cell) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                reveal(this.point);
                if (countFlaggedNeighbours(point) == cells[point.x][point.y].neighbours) {
                    revealNeighbours(point);
                }
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                if (cell.flagged) {
                    cell.flagged = false;
                    return;
                }
                if (cell.revealed)
                    return;
                cell.flagged = true;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Pair now = MouseOnCell();
            if (now.x == -1)
                return;
            this.point = now;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Pair now = MouseOnCell();
            if (now.x == -1)
                return;
            if (now.equals(this.point)) {
                cellClicked(e, cells[this.point.x][this.point.y]);
            }
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

    public void reveal(Pair point) {
        Cell cell = cells[point.x][point.y];
        if (cell.revealed || cell.flagged)
            return;
        cell.revealed = true;
        if (cell.mine) {
            return;
        }
        if (cell.neighbours != 0)
            return;

        revealNeighbours(point);

    }

    private int countFlaggedNeighbours(Pair point) {
        int n = 0;
        int[] d = { 0, 1, -1 };
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (l == 0 && k == 0)
                    continue;
                int nx = point.x + d[k];
                int ny = point.y + d[l];
                if (nx < 0 || ny < 0 || nx >= X || ny >= Y)
                    continue;
                n += cells[nx][ny].flagged ? 1 : 0;
            }
        }
        return n;
    }

    private void revealNeighbours(GUI.Pair point) {
        int[] d = { 0, 1, -1 };
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if (l == 0 && k == 0)
                    continue;
                int nx = point.x + d[k];
                int ny = point.y + d[l];
                if (nx < 0 || ny < 0 || nx >= X || ny >= Y)
                    continue;
                reveal(new Pair(nx, ny));
            }
        }
    }

    void getImages() {
        flag = new ImageIcon("src\\Images\\red-flag.png").getImage();
        flag = flag.getScaledInstance((WIDTH * 8 / 10), (HEIGHT * 8 / 10), Image.SCALE_SMOOTH);
        bomb = new ImageIcon("src\\Images\\Bomb.png").getImage();
        bomb = bomb.getScaledInstance((WIDTH * 8 / 10), (HEIGHT * 8 / 10), Image.SCALE_SMOOTH);
    }

    public Color ColorOfNum(int num) {
        switch (num) {
            case 1:
                return Color.decode("#1976d2");
            case 2:
                return Color.decode("#388e3c");
            case 3:
                return Color.decode("#d32f2f");
            case 4:
                return Color.decode("#7b1fa2");
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

        @Override
        public String toString() {
            return this.x + " " + this.y;
        }

        @Override
        public boolean equals(Object object2) {
            return object2 instanceof Pair && x == (((Pair) object2).x) && y == (((Pair) object2).y);
        }

    }
}