package Game;

import org.jbox2d.common.Vec2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class Controls {
    private final GameView view;
    private final Player player;
    private final GameWorld world;

    protected Controls(GameView view, Player player, GameWorld world) {
        this.view = view;
        this.player = player;
        this.world = world;
        addKeyboardInputs();
        addMouseInputs();
    }

    private void addMouseInputs() {
        view.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                player.attack();
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    private void addKeyboardInputs() {
        view.addKeyListener(new KeyAdapter() {
            int keyPressed;
            int keyReleased;
            @Override
            public void keyPressed(KeyEvent e) {
                if (!world.isPaused) {
                    keyPressed = e.getKeyCode();
                    if (keyPressed == KeyEvent.VK_A) {
                        player.startWalking(-7);
                    } else if (keyPressed == KeyEvent.VK_D) {
                        player.startWalking(7);
                    } else if (keyPressed == KeyEvent.VK_1) {
                        world.debugOn();
                    } else if (keyPressed == KeyEvent.VK_SPACE || keyPressed == KeyEvent.VK_W) {
                        player.jump(10);
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (!world.isPaused) {
                    keyReleased = e.getKeyCode();
                    // the second condition in the "&&" allows inputs to jitter between keys without stutters in the movement and that linear velocity is handles properly during a jump
                    if ((keyReleased == KeyEvent.VK_A || keyReleased == KeyEvent.VK_D) && (keyPressed == keyReleased || keyPressed == KeyEvent.VK_SPACE)) {
                        player.stopWalking();
                        player.setLinearVelocity(new Vec2(0, player.getLinearVelocity().y));
                    }
                }
            }
        });
    }
}
