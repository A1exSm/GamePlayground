package Game;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.event.*;

public class GameWorld extends World {
    private final GameView view;
    private final Player player;
    private boolean debugOn;
    private String alertString;
    private javax.swing.Timer alertTimer;
    protected boolean lastRight = true;
    protected boolean isPaused = false;

    // constructor
    public GameWorld() {
        super();
        this.view = new GameView(this, 1200, 630);
        GameFrame frame = new GameFrame("GamePlayground", this.view);
        player = new Player(this);
        new GameMenu(frame, this);
        new PlayerStepListener(player, this);
        new Controls(view, player, this);
        populate();
        viewTracker();
        secondsTimer();
        // end of constructor start of a new world :)
        start();
    }
    // debug methods
    protected void debugOn() {
        view.setGridResolution(!debugOn ? 1 : 0);
        for (Body body : getDynamicBodies()) {
            body.setAlwaysOutline(!debugOn);
        }
        debugOn = !debugOn;
    }
    // population methods
    private void populate() {
        Ground ground = new Ground(this, new Vec2(500, 0.5f), new Vec2(0, -0.5f));
        new Ground(this, new Vec2(2, 2f), new Vec2(-5, 2f));
        new DynamicBody(this, new DynamicPolygon(new Vec2[]{new Vec2(0, 0), new Vec2(), new Vec2(6, 0), new Vec2(6, 2), new Vec2(4, 4), new Vec2(2, 4), new Vec2(0, 2), new Vec2(0, 0)}));
        SolidFixture groundFixture = new SolidFixture(ground, new BoxShape(10, 1f));
        new Ground.Platform(this, new Vec2(20, 4));
        new Ground.Platform(this, new Vec2(27, 7));
        new Trampoline(this, new Vec2(-20, 1));
    }
    // Method Override
    private void secondsTimer() {
        alertTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alertString = null;
                view.timeUpdate();
            }
        });
        alertTimer.setRepeats(true);
        alertTimer.start();
    }
    private void viewTracker() {
        addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent event) {
                view.setCentre(player.getPosition());
            }
            @Override
            public void postStep(StepEvent event) {
            }
        });
    }
    // settings
    StaticBody itemA;
    protected void togglePause() {
        if (isPaused) {
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
