package Game;

import javax.swing.*;

class GameFrame extends JFrame {
    public GameFrame(String title, GameView view) {
        super(title);
        add(view);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(true);
        setVisible(true);
        pack();
    }
}
