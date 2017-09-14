package sasd97.java_blog.xyz.background_picker.models;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import com.github.sasd97.lib_gradientview.models.Gradient;

import org.w3c.dom.Text;

import sasd97.java_blog.xyz.libs_common.utils.models.ScalableImage;
import sasd97.java_blog.xyz.libs_common.utils.models.TextColor;

/**
 * Created by alexander on 08/09/2017.
 */

public class ImageItem implements BackgroundItem {

    private ScalableImage image;
    private TextColor textColor = TextColor.WHITE;

    public ImageItem(@NonNull ScalableImage image) {
        this.image = image;
    }

    public int getThumb() {
        return image.getThumb();
    }

    public ScalableImage getImage() {
        return this.image;
    }

    public void setTextColor(@NonNull TextColor textColor) {
        this.textColor = textColor;
    }

    @Override
    public TextColor getColor() {
        return textColor;
    }
    
    @Override
    public int getType() {
        return IMAGE;
    }
}
