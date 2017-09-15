package sasd97.java_blog.xyz.gallery_picker.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by alexander on 16/09/2017.
 */

public class StartOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int offsetPx;
    private int gridItemCount;

    public StartOffsetItemDecoration(int offsetPx,
                                     int gridItemCount) {
        this.offsetPx = offsetPx;
        this.gridItemCount = gridItemCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) > gridItemCount - 1) {
            return;
        }

        if (offsetPx > 0) {
            outRect.left = offsetPx;
        }
    }
}
