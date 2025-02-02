package Game;
import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;


public class GameWorld extends World {
    private UserView view;
    private Walker player;
    ArrayList<SolidFixture> playerFixtureList;
    private boolean debugOn;
    // constructor
    public GameWorld() {
        super();
        viewSetup();
        setupJFrame();
        populate();
        setPlayer();
        keyboardInputs();
        viewTracker();
        // end of constructor start of a new world :)
        start();
    }
    // setup methods
    private void viewSetup() {
        view = new UserView(this, 900, 900);
        view.setFocusable(true);
        view.requestFocus();
        view.setFocusable(true);
    }
    private void setupJFrame() {
        JFrame frame = new JFrame("GamePlayground");
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);
    }
//    private void simSet() {
//        SimulationSettings sim = new SimulationSettings(60);
//    }
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
             new Vec2(0, 0), new Vec2(), new Vec2(6,0), new Vec2(6,2), new Vec2(4,4), new Vec2(2,4), new Vec2(0,2), new Vec2(0,0)
        }))).setPosition(new Vec2(6,1));
        SolidFixture groundFixture = new SolidFixture(ground, new BoxShape(10, 1f));
//        groundFixture.setFriction(5f);
    }
    private List<Vec2> polygon(Vec2[] points) {
        return new ArrayList<>(Arrays.asList(points));
    }
    // player methods
    private void setPlayer() {
        Shape playerShape = new BoxShape(1,2);
        player = new Walker(this, playerShape);
        player.setPosition(new Vec2(0, 3f));
        addPlayerFixtures();
    }
    private void addPlayerFixtures() {
        playerFixtureList = new ArrayList<>(Arrays.asList(new SolidFixture(player, new BoxShape(0.4f,1f, new Vec2(-0.6f, -3f))),new SolidFixture(player, new BoxShape(0.4f,1f, new Vec2(0.6f, -3f)))));
//        for (SolidFixture solidFixture : playerFixtureList) {
//            solidFixture.setFriction(20f);
//        }
    }

    private void Stairs(Vec2 halfStepDimensions, Vec2 originPos, int numSteps, String vertical, String horizontal) {
        if (vertical.equals("DOWN")) halfStepDimensions.y = -halfStepDimensions.y;
        if (horizontal.equals("LEFT")) halfStepDimensions.x = -halfStepDimensions.x;
        for (int i = 0; i < numSteps; i++) {
            new StaticBody(this, new BoxShape(halfStepDimensions.x, halfStepDimensions.y, new Vec2(originPos.x + ((halfStepDimensions.x*2) * i), originPos.y + ((halfStepDimensions.y*2) *i))));
        }
    }
    // Method Override
    private void viewTracker() {
        addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent event) {
                view.setCentre(player.getPosition());
            }
            @Override
            public void postStep(StepEvent event) {}
        });
    }
    /*
    private void playerCollisions() {
        player.addCollisionListener(e -> {
            if (!e.getOtherBody().getName().equals("Ground0")) {
                if (e.getNormal().y == 0) {
                    System.out.println("collided with " + e.getOtherBody().getName());

                }
            }
        });
    }
     */
    private void keyboardInputs() {
        view.addKeyListener(new KeyAdapter() {
            int keyPressed;
            int keyReleased;
           @Override
           public void keyPressed(KeyEvent e) {
               keyPressed = e.getKeyCode();
               if (keyPressed == KeyEvent.VK_A || keyPressed == KeyEvent.VK_D) player.startWalking((keyPressed==KeyEvent.VK_A) ? -6 : 6);
               else if (keyPressed == KeyEvent.VK_1) debugOn();
               else if (keyPressed == KeyEvent.VK_SPACE) player.jump(10);
           }
           @Override
            public void keyReleased(KeyEvent e) {
               keyReleased = e.getKeyCode();
               // the second condition in the "&&" allows inputs to jitter between keys without stutters in the movement and that linear velocity is handles properly during a jump
               if ((keyReleased == KeyEvent.VK_A || keyReleased == KeyEvent.VK_D) && (keyPressed == keyReleased || keyPressed == KeyEvent.VK_SPACE)) {
                   player.stopWalking();
                   player.setLinearVelocity(new Vec2(0, player.getLinearVelocity().y));
               }
           }
        });
    }
}
