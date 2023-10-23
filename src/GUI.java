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

    public GUI() {
        this.setTitle("Minesweeper");
        // this.setSize(1286, 829); //Original Vals 1280+6, 800+29
        this.setSize(X * WIDTH + 13, (Y + 1) * HEIGHT + 37);
        this.setBackground(Color.DARK_GRAY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
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
                    if (mx >= recx && mx <= recx + recw && my >= recy && my <= recy + rech)
                        g.setColor(Color.BLACK);
                    g.fillRect(recx, recy, recw, rech);
                }
            }
        }
    }

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub
            System.out.println("Dragged");
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // System.out.println("Moved");
            mx = e.getX() - 6;
            my = e.getY() - 30;
            System.out.println(mx + " " + my);
        }
    }

    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

            System.out.println("Clicked");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
        }

    }
}