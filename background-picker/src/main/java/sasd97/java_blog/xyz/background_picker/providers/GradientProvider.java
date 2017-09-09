package sasd97.java_blog.xyz.background_picker.providers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.github.sasd97.lib_gradientview.models.Gradient;

import java.util.ArrayList;
import java.util.List;

import sasd97.java_blog.xyz.background_picker.R;

/**
 * Created by alexander on 09/09/2017.
 */

public class GradientProvider implements Provider<List<Gradient>> {

    private Context context;
    private List<Gradient> gradients = new ArrayList<>();

    public GradientProvider(@NonNull Context context) {
        this.context = context;
    }

    private void init() {
        int[] gradientsRaw = context.getResources().getIntArray(R.array.gradients);

        for (int i = 0; i < gradientsRaw.length; i += 2) {
            gradients.add(new Gradient(gradientsRaw[i], gradientsRaw[i + 1]));
        }
    }

    @Override
    public List<Gradient> provide() {
        if (gradients.isEmpty()) init();
        return gradients;
    }
}
