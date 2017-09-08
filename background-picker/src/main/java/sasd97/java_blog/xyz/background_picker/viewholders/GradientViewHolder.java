package sasd97.java_blog.xyz.background_picker.viewholders;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.github.sasd97.lib_gradientview.GradientView;

import sasd97.java_blog.xyz.background_picker.R;
import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.background_picker.models.GradientItem;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_selectionview.SelectionView;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 08/09/2017.
 */

public class GradientViewHolder extends BackgroundViewHolder {

    private View rootView;
    private GradientView gradientView;
    private SelectionView selectionView;

    public GradientViewHolder(@NonNull View itemView,
                              @NonNull OnItemClickListener<View> listener) {
        super(itemView, listener);

        rootView = itemView.findViewById(R.id.rootView);
        gradientView = itemView.findViewById(R.id.gradientView);
        selectionView = itemView.findViewById(R.id.selectionView);

        rootView.setOnClickListener(this);

        selectionView.add(new Selection(8, Color.parseColor("#528bcc")));
        selectionView.add(new Selection(4, Color.WHITE));
    }

    @Override
    public void select() {
        selectionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void deselect() {
        selectionView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setBackgroundItem(@NonNull BackgroundItem item) {
        if (!(item instanceof GradientItem)) return;
        GradientItem gradientItem = (GradientItem) item;

        gradientView.setGradient(gradientItem.getGradient());
    }
}
