package sasd97.java_blog.xyz.libs_common.utils.models;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alexander on 13/09/2017.
 */

public class ScalableImage {

    public static class Pair {

        public int resource;
        public int gravityRule;
        public int width = ViewGroup.LayoutParams.MATCH_PARENT;
        public int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        public ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_INSIDE;

        public Pair(int resource,
                    int gravityRule) {
            this.resource = resource;
            this.gravityRule = gravityRule;
        }

        public Pair(int resource,
                    int gravityRule,
                    int width, int height,
                    ImageView.ScaleType scaleType) {
            this.width = width;
            this.height = height;
            this.resource = resource;
            this.scaleType = scaleType;
            this.gravityRule = gravityRule;
        }
    }

    private int thumb;
    private List<Pair> elements;

    private ScalableImage(@NonNull Builder builder) {
        this.thumb = builder.thumb;
        this.elements = builder.elements;
    }

    public int getThumb() {
        return thumb;
    }

    public List<Pair> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScalableImage{");
        sb.append(", elements=").append(elements);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private int thumb;
        private List<Pair> elements;

        public Builder thumb(int thumb) {
            this.thumb = thumb;
            return this;
        }

        public Builder elements(Pair... elements) {
            this.elements = Arrays.asList(elements);
            return this;
        }

        public ScalableImage build() {
            return new ScalableImage(this);
        }
    }
}
