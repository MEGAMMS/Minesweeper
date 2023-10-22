import javax.swing.*;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {
    // set up
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int X = 16;
    public static final int Y = 9;
    public static final int SPACING = 4;

    public GUI() {
        this.setTitle("Minesweeper");
        // this.setSize(1286, 829); //Original Vals 1280+6, 800+29
        this.setSize(X * WIDTH + 13, Y * HEIGHT + 37);
        this.setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        Board board = new Board();
        this.setContentPane(board);
    }

    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.gray);
            for (int i = 0; i < X; i++) {
                for (int j = 0; j < Y; j++) {
                    g.fillRect(SPACING + i * GUI.WIDTH, SPACING + (j + 1) * GUI.HEIGHT,
                            GUI.WIDTH - 2 * SPACING, GUI.HEIGHT - 2 * SPACING);
                }
            }
        }
    }
}