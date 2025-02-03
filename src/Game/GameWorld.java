package Game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class GameWorld extends World {
    private GameView view;
    private Player player;
    ArrayList<SolidFixture> playerFixtureList;
    private boolean debugOn;
    private boolean isPaused;
    private String alertString;
    private final javax.swing.Timer alertTimer;
    private GameFrame frame;
    protected boolean lastRight = true;
    protected static boolean isAttacking = false;

    // constructor
    public GameWorld() {
        super();
        this.view = new GameView(this, 1200, 630);
        frame = new GameFrame("GamePlayground", this.view);
        new GameMenu(frame, this);
        populate();
        player = new Player(this);
        keyboardInputs();
        mouseInputs();
        viewTracker();
        alertTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alertString = null;
                view.timeUpdate();
            }
        });
        alertTimer.setRepeats(true);
        alertTimer.start();
        // end of constructor start of a new world :)
        isPaused = false;
        start();
    }

    // debug methods
    private void debugOn() {
        view.setGridResolution(!debugOn ? 1 : 0);
        debugOn = !debugOn;
    }

    // population methods
    private void populate() {
        Ground ground = new Ground(this, new Vec2(500, 0.5f), new Vec2(0, -0.5f));
        new Ground(this, new Vec2(2, 2f), new Vec2(-5, 2f));
        new DynamicBody(this, new PolygonShape(polygon(new Vec2[]{
                new Vec2(0, 0), new Vec2(), new Vec2(6, 0), new Vec2(6, 2), new Vec2(4, 4), new Vec2(2, 4), new Vec2(0, 2), new Vec2(0, 0)
        }))).setPosition(new Vec2(6, 1));
        SolidFixture groundFixture = new SolidFixture(ground, new BoxShape(10, 1f));
//        groundFixture.setRestitution(1f);
        new Ground.Platform(this, new Vec2(20, 4));
        new Ground.Platform(this, new Vec2(27, 7));
        new Trampoline(this, new Vec2(-20, 1));
    }

    private List<Vec2> polygon(Vec2[] points) {
        return new ArrayList<>(Arrays.asList(points));
    }

    private void addPlayerFixtures() {
        playerFixtureList = new ArrayList<>(Arrays.asList(new SolidFixture(player, new BoxShape(0.4f, 1f, new Vec2(-0.6f, -3f))), new SolidFixture(player, new BoxShape(0.4f, 1f, new Vec2(0.6f, -3f)))));
    }

    private void Stairs(Vec2 halfStepDimensions, Vec2 originPos, int numSteps, String vertical, String horizontal) {
        if (vertical.equals("DOWN")) halfStepDimensions.y = -halfStepDimensions.y;
        if (horizontal.equals("LEFT")) halfStepDimensions.x = -halfStepDimensions.x;
        for (int i = 0; i < numSteps; i++) {
            new StaticBody(this, new BoxShape(halfStepDimensions.x, halfStepDimensions.y, new Vec2(originPos.x + ((halfStepDimensions.x * 2) * i), originPos.y + ((halfStepDimensions.y * 2) * i))));
        }
    }

    // Method Override
    private void viewTracker() {
        addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent event) {
                view.setCentre(player.getPosition());
                Vec2 tempV = player.getLinearVelocity();
                if (!isAttacking) {
                    if (tempV.x > 2) {
                        lastRight = true;
                        if (tempV.y > 0) player.action("JUMPr");
                        else if (tempV.y < 0) player.action("FALLr");
                        else player.action("RIGHT");
                    } else if (tempV.x < -2) {
                        lastRight = false;
                        if (tempV.y > 0) player.action("JUMPl");
                        else if (tempV.y < 0) player.action("FALLl");
                        else player.action("LEFT");
                    } else {
                        if (tempV.y < 2 && tempV.y > -2) {
                            if (lastRight) player.action("IDLEr");
                            else player.action("IDLEl");
                        } else if (lastRight) player.action("JUMPr");
                        else player.action("JUMPl");
                    }
                } else {
                    if (lastRight) player.action("ATTACKr");
                    else player.action("ATTACKl");
                    javax.swing.Timer attackTimer = new javax.swing.Timer(800, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            GameWorld.isAttacking = false;
                        }
                    });
                    attackTimer.setRepeats(false);
                    attackTimer.start();
                }
            }

            @Override
            public void postStep(StepEvent event) {
            }
        });
    }

    private void mouseInputs() {
        view.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                player.attack();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void keyboardInputs() {
        view.addKeyListener(new KeyAdapter() {
            int keyPressed;
            int keyReleased;

            @Override
            public void keyPressed(KeyEvent e) {
                if (!isPaused) {
                    keyPressed = e.getKeyCode();
                    if (keyPressed == KeyEvent.VK_A) {
                        player.startWalking(-7);
                    } else if (keyPressed == KeyEvent.VK_D) {
                        player.startWalking(7);
                    } else if (keyPressed == KeyEvent.VK_1) debugOn();
                    else if (keyPressed == KeyEvent.VK_SPACE || keyPressed == KeyEvent.VK_W) player.jump(10);
//                    else player.attack();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!isPaused) {
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
    StaticBody itemA;
    protected void togglePause() {
        if (isPaused) {
            if (getStaticBodies().contains(itemA)) {
                itemA.destroy();
            }
            isPaused = false;
            alertTimer.start(); // at one point the timer stopped with the view, now it does not, what is even going on, I even rolled back the VCS and none of their scopes have changed :(
            start();
        }
        else {
            isPaused = true;
            alertTimer.stop();
            stop();
        }
    }
}
