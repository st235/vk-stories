package sasd97.java_blog.xyz.gallery_picker.components;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by alexander on 09/09/2017.
 */

public class SquareView extends AppCompatImageView {

    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getMeasuredWidth() < getMeasuredHeight()) setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
        else setMeasuredDimension(heightMeasureSpec, heightMeasureSpec);
    }
}
