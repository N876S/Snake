import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Font;

public class Snake implements KeyListener {

    Random random = new Random();

    public static int tileSize = 40;
    public int snakeX = 0;
    public int snakeY = 0;
    public int snakeLength = 5;

    public int direction = 0;
    public int bestRecord = 0;
    /*
     * 0 = right
     * 1 = up
     * 2 = left
     * 3 = down
     */

    public int snakeSpeed = tileSize / 8;
    public int winScore = ((Window.width / tileSize) * (Window.height / tileSize)) - 5;

    public int appleX = Math.round(random.nextInt(Window.width / tileSize));
    public int appleY = Math.round(random.nextInt(Window.height / tileSize));

    ArrayList<Integer> snakeXTiles = new ArrayList<Integer>();
    ArrayList<Integer> snakeYTiles = new ArrayList<Integer>();

    public boolean ded = false;
    public boolean moved = false;
    public static boolean winner = false;

    Color snakeHead = new Color(19, 212, 70);

    public void updateSnake() {
        for (int i = 0; i < snakeXTiles.size(); i++) {
            if (appleX == snakeXTiles.get(i) / tileSize && appleY == snakeYTiles.get(i) / tileSize) {
                appleX = Math.round(random.nextInt(Window.width / tileSize));
                appleY = Math.round(random.nextInt(Window.height / tileSize));
            }
        }
        // snake touches the walls
        if (snakeX > Window.width - tileSize || snakeX < 0 || snakeY > Window.height - tileSize || snakeY < 0) {
            gameOver();
        }
        for (int i = 0; i < snakeXTiles.size() - 1; i++) {
            if (snakeXTiles.get(i) == snakeX && snakeYTiles.get(i) == snakeY) {
                gameOver();
            }
        }
        if (winScore == snakeLength - 5) {
            winner = true;
        }

        // checks if die
        if (ded == false) {
            snakeSpeed--;
            if (snakeSpeed < 0) {
                if (direction == 0) {
                    snakeX += tileSize;
                } else if (direction == 1) {
                    snakeY += tileSize;
                } else if (direction == 2) {
                    snakeX -= tileSize;
                } else if (direction == 3) {
                    snakeY -= tileSize;
                }
                moved = false;
                snakeXTiles.add(snakeX);
                snakeYTiles.add(snakeY);
                if (snakeXTiles.size() > snakeLength) {
                    snakeXTiles.remove(0);
                    snakeYTiles.remove(0);
                }
                snakeSpeed = tileSize / 8;
            }
            // snake touches the apple
            if (snakeX == appleX * tileSize && snakeY == appleY * tileSize) {
                snakeLength++;

                appleX = Math.round(random.nextInt(Window.width / tileSize));
                appleY = Math.round(random.nextInt(Window.height / tileSize));
            }
        }
    }

    public void gameOver() {
        ded = true;
        direction = 4;
    }

    public void drawSnake(Graphics g) {
        // TILES
        for (int i = 0; i < Window.width; i += tileSize) {
            for (int j = 0; j < Window.height; j += tileSize) {
                g.setColor(Color.black);
                g.drawRect(i, j, tileSize, tileSize);
            }
        }

        // apple
        g.setColor(Color.RED);
        g.fillRect(appleX * tileSize + 1, appleY * tileSize + 1, tileSize - 1, tileSize - 1);

        // snake
        for (int i = 0; i < snakeXTiles.size(); i++) {
            if (i == snakeXTiles.size() - 1) {
                g.setColor(snakeHead);
            } else {
                g.setColor(Color.GREEN);
            }

            g.fillRect(snakeXTiles.get(i) + 1, snakeYTiles.get(i) + 1, tileSize - 1, tileSize - 1);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        g.drawString("Score: " + (snakeLength - 5), 10, 45);
        g.drawString("Best Score: " + (bestRecord), 10, 85);

        // check for the best score
        if (snakeLength - 5 > bestRecord) {
            bestRecord = snakeLength - 5;
        }

        if (ded == true) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            g.drawString("GAME OVER", Window.width / 2 - 290, Window.height / 2);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g.drawString("press space to restart", Window.width / 2 - 170, Window.height / 2 + 30);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (moved == false) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != 2) {
                direction = 0;
                moved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != 3) {
                direction = 1;
                moved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != 0) {
                direction = 2;
                moved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_UP && direction != 1) {
                direction = 3;
                moved = true;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            snakeLength = 5;
            direction = 0;
            snakeX = 0;
            snakeY = 0;
            snakeXTiles.clear();
            snakeYTiles.clear();
            appleX = Math.round(random.nextInt(Window.width / tileSize));
            appleY = Math.round(random.nextInt(Window.height / tileSize));
            ded = false;
            winner = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
