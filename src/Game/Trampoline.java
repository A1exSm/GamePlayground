package Game;

import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import org.jbox2d.common.Vec2;
import city.cs.engine.BodyImage;

class Trampoline {
    private Ground midPiece;
    protected Trampoline(GameWorld world, Vec2 pos) {
        midPiece = new Ground(world, new Vec2(2,0.2f), pos);
        SolidFixture legL = new SolidFixture(midPiece, new BoxShape(0.25f, 1, new Vec2(-2.25f, -0.2f)));
        SolidFixture lefR = new SolidFixture(midPiece, new BoxShape(0.25f, 1, new Vec2(2.25f, -0.2f)));
        SolidFixture bouncyPart = new SolidFixture(midPiece, new BoxShape(2, 0.25f));
        bouncyPart.setRestitution(2f);
        midPiece.addImage(new BodyImage("data/Trampoline.png", 5f));
    }
}
