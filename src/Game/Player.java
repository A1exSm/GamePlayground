package Game;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.Walker;
import org.jbox2d.common.Timer;
import org.jbox2d.common.Vec2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Player extends Walker {
    private final Vec2 halfSize = new Vec2(1f, 2f);
    private final Shape shape  = new BoxShape(halfSize.x, halfSize.y);
    private boolean timer = false;

    protected Player(GameWorld world) {
        super(world, new BoxShape(1, 2));
        setPosition(new Vec2(0, 3f));
        setAlwaysOutline(true);
        action("IDLEr");
    }
    protected void action(String direction) {
        removeAllImages();
        switch (direction) {
            // I did not know that .flipHorizontal() existed so i used two separate images for so long :(
            case "IDLEr" -> addImage(new BodyImage("data/gifs/idle.gif", 18f));
            case "IDLEl" -> addImage(new BodyImage("data/gifs/idle.gif", 18f)).flipHorizontal();
            case "RIGHT" -> addImage(new BodyImage("data/gifs/run.gif", 18f));
            case "LEFT" -> addImage(new BodyImage("data/gifs/run.gif", 18f)).flipHorizontal();
            case "JUMPr" -> addImage(new BodyImage("data/gifs/jump1.png", 18f));
            case "JUMPl" -> addImage(new BodyImage("data/gifs/jump1.png", 18f)).flipHorizontal();
            case "FALLr" -> addImage(new BodyImage("data/gifs/fall1.png", 18f));
            case "FALLl" -> addImage(new BodyImage("data/gifs/fall1.png", 18f)).flipHorizontal();
            case "ATTACKr" -> addImage(new BodyImage("data/gifs/attack1.gif", 18f));
            case "ATTACKl" -> addImage(new BodyImage("data/gifs/attack1.gif", 18f)).flipHorizontal();
        }
    }

    protected void attack() {
        if (!GameWorld.isAttacking) {
            GameWorld.isAttacking = true;
        }
    }
}


