package sasd97.java_blog.xyz.background_picker.models;

import sasd97.java_blog.xyz.libs_common.utils.models.ComplementaryColor;

/**
 * Created by alexander on 09/09/2017.
 */

public class PlusItem implements BackgroundItem {

    @Override
    public int getType() {
        return PLUS;
    }

    public ComplementaryColor getComplementaryColor() {
        return ComplementaryColor.WHITE;
    }
}
