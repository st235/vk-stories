package sasd97.java_blog.xyz.libs_editorview;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * Created by alexander on 14/09/2017.
 */

public abstract class OnTextChangedListener implements TextWatcher {

    public abstract void onEmpty();
    public abstract void onTextPresent();

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (TextUtils.isEmpty(charSequence)) {
            onEmpty();
            return;
        }

        onTextPresent();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
