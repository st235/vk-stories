package sasd97.java_blog.xyz.background_picker.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.github.sasd97.lib_gradientview.GradientView;
import com.github.sasd97.lib_gradientview.models.Gradient;

import java.util.List;

import sasd97.java_blog.xyz.background_picker.R;
import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.background_picker.models.GradientItem;
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 08/09/2017.
 */

public class GradientViewHolder extends BackgroundViewHolder {

    private Context context;
    private GradientView gradientView;

    private Provider<List<Selection>> selections;

    public GradientViewHolder(@NonNull View itemView,
                              @NonNull OnItemClickListener<View> listener,
                              @NonNull Provider<List<Selection>> selections) {
        super(itemView, listener, selections);

        this.selections = selections;
        context = itemView.getContext();
        setSelectionView(R.id.selectionView);

        gradientView = itemView.findViewById(R.id.gradientView);
    }

    @Override
    public void setBackgroundItem(@NonNull BackgroundItem item) {
        if (!(item instanceof GradientItem)) return;
        GradientItem gradientItem = (GradientItem) item;
        Gradient gradient = gradientItem.getGradient();

        if (gradient.isMonochrome() && gradient.getStartColor() == Color.WHITE) {
            int greyColor = ContextCompat.getColor(context, R.color.colorGreyLight);
            gradientView.setGradient(new Gradient(greyColor, greyColor));
            return;
        }

        gradientView.setGradient(gradient);
    }
}
