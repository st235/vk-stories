package sasd97.java_blog.xyz.libs_touchlistener;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by alexander on 15/09/2017.
 */

public class RemoveRegionProvider {

    private View view;

    public RemoveRegionProvider(@NonNull View view) {
        this.view = view;
    }

    public Point getRemoveRegion() {
        return getViewCenterCoordinatesInScreen(view);
    }

    @NonNull
    private Point getViewCenterCoordinatesInScreen(@NonNull View view) {
        int[] coordinates = new int[2];
        view.getLocationOnScreen(coordinates);

        int centerX = coordinates[0] + view.getWidth() / 2;
        int centerY = coordinates[1] + view.getHeight() / 2;

        return new Point(centerX, centerY);
    }
}
