package Game;
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.SolidFixture;
import org.jbox2d.common.Vec2;
class Stairs extends StaticBody {
    public Stairs(GameWorld gameWorld, Vec2 halfStepDimensions, Vec2 originPos, int numSteps, String vertical, String horizontal) {
        super(gameWorld, new BoxShape(halfStepDimensions.x, halfStepDimensions.y));
        this.setPosition(originPos);
        if (vertical.equals("DOWN")) halfStepDimensions.y = -halfStepDimensions.y;
        if (horizontal.equals("LEFT")) halfStepDimensions.x = -halfStepDimensions.x;
        for (int i = 1; i < numSteps; i++) {
            new SolidFixture(this, new BoxShape(halfStepDimensions.x, halfStepDimensions.y, new Vec2(((halfStepDimensions.x*2) * i), (halfStepDimensions.y*2) *i )));
        }
    }
}
