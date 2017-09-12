package sasd97.java_blog.xyz.libs_common.utils.components;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by alexander on 12/09/2017.
 */

public class StoryButton extends AppCompatImageView implements View.OnClickListener {

    public interface OnStateChangedListener {
        void onStateChanged(int state);
    }

    private int current;
    private int[] states;
    private OnStateChangedListener listener;

    public StoryButton(Context context) {
        super(context);
        onInit();
    }

    public StoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public StoryButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    public void setStates(int[] states) {
        this.states = states;
    }

    private void onInit() {
        setOnClickListener(this);
    }

    public void setOnStateChangedListener(OnStateChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        current = (current + 1) % states.length;
        if (listener != null) listener.onStateChanged(current);
    }
}
