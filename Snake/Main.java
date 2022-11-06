
import javax.swing.JPanel;
import java.awt.Graphics;

public class Main extends JPanel implements Runnable {

    // gameloop variables
    Thread thread = new Thread(this);
    boolean running;
    double updateRate = 1.0d / 60.0d;
    long nextStatTime;
    int fps;
    int ups;

    // snake object
    Snake snake = new Snake();

    // constructor
    public Main() {
        thread.start();
    }

    // gameloop
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime = System.currentTimeMillis();
        long lastUpdate = System.currentTimeMillis();
        double lastRenderTimeInSeconds = currentTime - lastUpdate;
        nextStatTime = System.currentTimeMillis() + 1000;

        while (running) {
            currentTime = System.currentTimeMillis();
            lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
            accumulator += lastRenderTimeInSeconds;
            lastUpdate = currentTime;

            while (accumulator > updateRate) {
                update();
                accumulator -= updateRate;
            }
            repaint();
            if (System.currentTimeMillis() > nextStatTime) {
                // System.out.println(ups);
                // System.out.println(fps);
                fps = 0;
                ups = 0;
                nextStatTime = System.currentTimeMillis() + 1000;
            }
        }
    }

    // updates 60 times per second
    public void update() {
        ups++;
        if (Snake.winner == false) {
            snake.updateSnake();
        }

    }

    // this updates as often as possible per second
    public void paintComponent(Graphics g) {
        fps++;
        super.paintComponent(g);
        if (Snake.winner == false) {
            snake.drawSnake(g);
        } else {
            g.drawString("You win!", 100, 100);
        }
    }
}