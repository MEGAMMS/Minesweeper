import javax.swing.*;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI() {
        this.setTitle("Minesweeper");
        // this.setSize(1286, 829); //Original Vals 1280+6, 800+29
        this.setSize(800, 600);
        this.setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        Board board = new Board();
        this.setContentPane(board);
    }

    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.red);
            g.fillRect(0, 0, 80, 80);
        }
    }
}