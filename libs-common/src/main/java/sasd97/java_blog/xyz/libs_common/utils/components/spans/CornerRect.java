package sasd97.java_blog.xyz.libs_common.utils.components.spans;

import android.support.annotation.IntDef;

/**
 * Created by alexander on 25/09/2017.
 */

public final class CornerRect {

    public static final int NORMAL = 0;
    public static final int ROUNDED_OUTSIDE = 1;
    public static final int ROUNDED_INSIDE = 2;

    @IntDef({ NORMAL, ROUNDED_OUTSIDE, ROUNDED_INSIDE })
    public @interface Type {
    }

    private int topLeftCorner = NORMAL;
    private int topRightCorner = NORMAL;
    private int bottomLeftCorner = NORMAL;
    private int bottomRightCorner = NORMAL;

    public void setTopBound(@Type int type) {
        topLeftCorner = type;
        topRightCorner = type;
    }

    public void setBottomBound(@Type int type) {
        bottomLeftCorner = type;
        bottomRightCorner = type;
    }

    public int getTopLeftCorner() {
        return topLeftCorner;
    }

    public int getTopRightCorner() {
        return topRightCorner;
    }

    public int getBottomLeftCorner() {
        return bottomLeftCorner;
    }

    public int getBottomRightCorner() {
        return bottomRightCorner;
    }

    public void reset() {
        topLeftCorner = NORMAL;
        topRightCorner = NORMAL;
        bottomLeftCorner = NORMAL;
        bottomRightCorner = NORMAL;
    }
}
