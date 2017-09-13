package sasd97.java_blog.xyz.libs_touchlistener.models;

import android.graphics.PointF;

/**
 * Created by alexander on 09/09/2017.
 */

public class Vector2 extends PointF {

    public Vector2() {
        super();
    }

    public Vector2(float x, float y) {
        super(x, y);
    }

    public static float getAngle(Vector2 vector1, Vector2 vector2) {
        vector1.normalize();
        vector2.normalize();
        double degrees = (180.0 / Math.PI) * (Math.atan2(vector2.y, vector2.x) - Math.atan2(vector1.y, vector1.x));
        return (float) degrees;
    }

    public void normalize() {
        float length = (float) Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
    }
}