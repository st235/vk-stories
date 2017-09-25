package sasd97.java_blog.xyz.libs_common.utils.utils;

import android.support.annotation.IntDef;

/**
 * Created by alexander on 25/09/2017.
 */

public class SpansHelper {

    public static final float EQUALITY_RADIUS = 15;

    public static final int EQUALS = 0;
    public static final int BIGGER_THAN = 1;
    public static final int SMALLER_THAN = 2;

    @IntDef({EQUALS, BIGGER_THAN, SMALLER_THAN})
    public @interface EqualityTypes {
    }

    @EqualityTypes
    public static int checkEquality(float one, float another) {
        return checkEquality(one, another, EQUALITY_RADIUS);
    }

    @EqualityTypes
    public static int checkEquality(float one, float another, float radius) {
        if (Math.abs(one - another) < radius) return EQUALS;
        if (one > another) return BIGGER_THAN;
        return SMALLER_THAN;
    }
}
