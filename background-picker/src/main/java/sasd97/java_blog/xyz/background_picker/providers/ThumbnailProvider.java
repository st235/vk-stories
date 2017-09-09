package sasd97.java_blog.xyz.background_picker.providers;

import java.util.ArrayList;
import java.util.List;

import sasd97.java_blog.xyz.background_picker.R;

/**
 * Created by alexander on 09/09/2017.
 */

public class ThumbnailProvider implements Provider<List<Integer>> {

    private List<Integer> thumbs = new ArrayList<>();

    public ThumbnailProvider() {
    }

    private void init() {
        thumbs.add(R.drawable.thumb_beach);
        thumbs.add(R.drawable.thumb_stars);
    }

    @Override
    public List<Integer> provide() {
        if (thumbs.isEmpty()) init();
        return thumbs;
    }
}
