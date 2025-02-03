package Game;

import city.cs.engine.UserView;

import javax.swing.*;
import java.awt.*;

class GameView extends UserView {
    GameWorld gameWorld;
    public GameView(GameWorld gameWorld, int width, int height) {
        super(gameWorld, width, height);
        this.requestFocus();
        this.setFocusable(true);
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(new ImageIcon("data/sky.png").getImage(), 0, 0, this);
    }
}
