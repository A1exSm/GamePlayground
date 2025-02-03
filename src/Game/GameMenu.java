package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class GameMenu extends MenuBar {
    private final MenuBar gameMenu = new MenuBar();
    protected GameMenu(JFrame frame, GameWorld gameWorld) {
        super();
        addSettings(gameWorld);
        frame.setMenuBar(gameMenu);
    }
    private void addSettings(GameWorld gameWorld) {
        Menu settings = new Menu("Settings");
        gameMenu.add(settings);
        MenuItem pause = new MenuItem("Pause", new MenuShortcut(KeyEvent.VK_P, false));
        settings.add(pause);
        pause.setActionCommand("pause");
        pause.addActionListener(i->{
            if (i.getActionCommand().equals("pause")) {
                gameWorld.togglePause();
                if (gameWorld.isRunning()) pause.setLabel("Pause");
                else pause.setLabel("Unpause");
            }
        });
    }
}
