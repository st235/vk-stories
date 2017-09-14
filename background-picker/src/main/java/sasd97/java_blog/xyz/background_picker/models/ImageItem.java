package sasd97.java_blog.xyz.background_picker.models;

import android.support.annotation.NonNull;

import sasd97.java_blog.xyz.libs_common.utils.models.ComplementaryColor;
import sasd97.java_blog.xyz.libs_common.utils.models.ScalableImage;

/**
 * Created by alexander on 08/09/2017.
 */

public class ImageItem implements BackgroundItem {

    private ScalableImage image;
    private ComplementaryColor complementaryColor = ComplementaryColor.WHITE;

    public ImageItem(@NonNull ScalableImage image) {
        this.image = image;
    }

    public int getThumb() {
        return image.getThumb();
    }

    public ScalableImage getImage() {
        return this.image;
    }

    public void setComplementaryColor(@NonNull ComplementaryColor complementaryColor) {
        this.complementaryColor = complementaryColor;
    }

    public ComplementaryColor getComplementaryColor() {
        return complementaryColor;
    }
    
    @Override
    public int getType() {
        return IMAGE;
    }
}
