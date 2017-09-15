package sasd97.java_blog.xyz.libs_common.utils.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import sasd97.java_blog.xyz.libs_common.R;

/**
 * Created by alexander on 12/09/2017.
 */

public class StoryEditText extends AppCompatEditText
    implements TextWatcher {

    private static final String BREAK = "\n";

    private static final int DEFAULT_MAX_LENGTH = 80;
    private static final float DEFAULT_TEXT_SIZE = 30.0f;
    private static final int DEFAULT_TEXT_EPS = 30;

    private int width;
    int beforeNextTypeLength = 0;

    public StoryEditText(Context context) {
        super(context);
        onInit();
    }

    public StoryEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public StoryEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit();
    }

    public void setSpan(@NonNull Object object) {
        getText().setSpan(object, 0, getText().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        invalidate();
    }

    private void onInit() {
        setHint(R.string.whatsNew);
        setGravity(Gravity.CENTER);
        setTypeface(null, Typeface.BOLD);
        setBackgroundColor(Color.TRANSPARENT);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXT_SIZE);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(DEFAULT_MAX_LENGTH);
        setFilters(filterArray);

        addTextChangedListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        beforeNextTypeLength = charSequence.length();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (beforeNextTypeLength > editable.length()) return;
        String source = editable.toString();
        String[] split = source.split(BREAK);

        if (split.length == 0) return;
        TextUtils.copySpansFrom(editable, 0, editable.length(), null, editable, 0);

        if (width - getPaint().measureText(split[split.length - 1]) > DEFAULT_TEXT_EPS) return;
        removeTextChangedListener(this);

        char last = editable.charAt(editable.length() - 1);
        editable.delete(editable.length() - 1, editable.length());

        append(BREAK);
        append(String.valueOf(last));

        addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
}
