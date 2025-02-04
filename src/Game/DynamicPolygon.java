package Game;

import city.cs.engine.PolygonShape;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DynamicPolygon extends PolygonShape {
    protected DynamicPolygon(Vec2[] points) {
        super(new ArrayList<>(Arrays.asList(points)));
    }
}
