package Game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PlayerStepListener implements StepListener {
    Player player;
    GameWorld world;
    private boolean lastRight = true;
    private javax.swing.Timer attackTimer;

    protected PlayerStepListener(Player player, GameWorld world) {
        this.player = player;
        this.world = world;
        world.addStepListener(this);
    }

    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 tempV = player.getLinearVelocity();
        if (!player.isAttacking) {
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
                } else if (lastRight) {
                    if (tempV.y > 0) player.action("JUMPr");
                    else player.action("FALLr");
                }
                else {
                    if (tempV.y < 0) player.action("JUMPl");
                    else player.action("FALLl");
                }
            }
        } else {
            if (lastRight) player.action("ATTACKr");
            else player.action("ATTACKl");
            javax.swing.Timer attackTimer = new javax.swing.Timer(1200, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.isAttacking = false;
//                    System.out.println("Running @" + world.view.Time());
                }
            });
            attackTimer.setRepeats(false);
            attackTimer.start();
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {}
}

