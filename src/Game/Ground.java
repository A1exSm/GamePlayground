package Game;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import org.jbox2d.common.Vec2;

class Ground extends StaticBody {
    private Vec2 halfDimensions;
    private Vec2 originPos;
    private static int count = -1;
    {
        count += 1;
    }
    // constructor
    public Ground(GameWorld gameWorld) {
        super(gameWorld);
    }
    public Ground(GameWorld gameWorld, Vec2 halfDimensions, Vec2 originPos) {
        super(gameWorld, new BoxShape(halfDimensions.x, halfDimensions.y));
        this.halfDimensions = halfDimensions;
        this.originPos = originPos;
        this.setPosition(originPos);
        this.setName("Ground"+count);
    }
    // getters
    public Vec2 getHalfDimensions() {
        return halfDimensions;
    }
    public Vec2 getOriginPos() {
        return originPos;
    }
    public static int getCount() {
        return count;
    }
}
