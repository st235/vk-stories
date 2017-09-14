package sasd97.java_blog.xyz.libs_common.utils.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;

import sasd97.java_blog.xyz.libs_common.R;
import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;

/**
 * Created by alexander on 12/09/2017.
 */

public class StoryEditText extends AppCompatEditText
    implements TextWatcher {

    private int width;

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
        setGravity(Gravity.CENTER);
        setHint(R.string.whatsNew);
        setTypeface(null, Typeface.BOLD);
        setBackgroundColor(Color.TRANSPARENT);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 32.0f);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(60);
        setFilters(filterArray);

        addTextChangedListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
    }

    int prevLength = 0;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        prevLength = charSequence.length();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (prevLength > editable.length()) return;

        String breakChar = "\n";
        String source = editable.toString();
        String[] split = source.split(breakChar);
        if (split.length == 0) return;

        for (String t: split) Log.d("HAHA", t);
        TextUtils.copySpansFrom(editable, 0, editable.length(), null, editable, 0);
        if (width - getPaint().measureText(split[split.length - 1]) > 20) return;
        removeTextChangedListener(this);
        char last = editable.charAt(editable.length() - 1);
        editable.delete(editable.length() - 2, editable.length() - 1);
        append(breakChar);
        append(String.valueOf(last));
        addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
}
