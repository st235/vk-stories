package sasd97.java_blog.xyz.gallery_picker.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by alexander on 16/09/2017.
 */

public class EndOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int offsetPx;
    private int gridItemCount;

    public EndOffsetItemDecoration(int offsetPx,
                                   int gridItemCount) {
        this.offsetPx = offsetPx;
        this.gridItemCount = gridItemCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemLimit = state.getItemCount() - state.getItemCount() % gridItemCount;
        if (parent.getChildAdapterPosition(view) < itemLimit) {
            return;
        }

        if (offsetPx > 0) {
            outRect.right = offsetPx;
        }
    }
}
