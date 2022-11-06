import javax.swing.JFrame;

import java.awt.Dimension;

public class Window extends JFrame {

    public static final int width = 800;
    public static final int height = 800;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Main main = new Main();
        frame.setPreferredSize(new Dimension(width + 17, height + 40));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Snake");
        frame.add(main);
        frame.pack();
        frame.addKeyListener(main.snake);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
