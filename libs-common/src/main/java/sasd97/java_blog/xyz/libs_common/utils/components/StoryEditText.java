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
import android.util.TypedValue;
import android.view.Gravity;

import sasd97.java_blog.xyz.libs_common.utils.utils.Dimens;

/**
 * Created by alexander on 12/09/2017.
 */

public class StoryEditText extends AppCompatEditText {

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
        setTypeface(null, Typeface.BOLD);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f);
        setBackgroundColor(Color.TRANSPARENT);
//        setInputType(InputType.TYPE_CLASS_TEXT);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(40);
        setFilters(filterArray);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                TextUtils.copySpansFrom(editable, 0, editable.length(), null, editable, 0);
            }
        });
    }
}
