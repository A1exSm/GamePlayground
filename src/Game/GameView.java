package Game;

import city.cs.engine.UserView;

import javax.swing.*;
import java.awt.*;

class GameView extends UserView {
    private GameWorld gameWorld;
    private static int time = 0;
    private Image background = new ImageIcon("data/sky.png").getImage();

    protected GameView(GameWorld gameWorld, int width, int height) {
        super(gameWorld, width, height);
        requestFocus();
        setFocusable(true);
    }

    protected void timeUpdate() {
        time++;
    }
    protected static int getTime() {
        return time;
    }

    @Override
    protected void paintBackground(Graphics2D g) {
//        background = background.getScaledInstance(800, 600, Image.SCALE_DEFAULT); // idk how this works :( can't get it to work
        g.drawImage(background, 0, 0, this);
    }
    public static final Font STATUS_FONT = new Font("Monospaced", Font.PLAIN, 20);

    @Override
    protected void paintForeground(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.setFont(STATUS_FONT);
        g.drawString("Timer: " + time/60 + ":" + time%60, 5, 20);
    }
}
