package sasd97.java_blog.xyz.libs_selectionview.providers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.libs_selectionview.R;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 09/09/2017.
 */

public class SelectionProvider implements Provider<List<Selection>> {

    private Context context;
    private List<Selection> selection = new ArrayList<>();

    public SelectionProvider(@NonNull Context context) {
        this.context = context;
    }

    private void init() {
        selection.add(new Selection(4, ContextCompat.getColor(context, R.color.colorCornflowerBlue)));
        selection.add(new Selection(2, ContextCompat.getColor(context, R.color.colorWhite)));
    }

    @Override
    public List<Selection> provide() {
        if (selection.isEmpty()) init();
        return selection;
    }
}
