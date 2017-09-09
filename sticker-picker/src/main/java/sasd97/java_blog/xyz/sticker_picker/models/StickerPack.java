package sasd97.java_blog.xyz.sticker_picker.models;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by alexander on 09/09/2017.
 */

public class StickerPack {

    private List<Sticker> stickers;

    public StickerPack() {
        stickers = new ArrayList<>();
    }

    public void addSticker(@NonNull String folder,
                           @NonNull String fileName) {
        Uri uri = getUriFromAssets(folder, fileName);
        this.stickers.add(new Sticker(uri));
    }

    private Uri getUriFromAssets(@NonNull String folder,
                                 @NonNull String fileName) {
        String sticker = String.format(Locale.US, "%1$s%2$s%3$s", folder, File.separator, fileName);
        String path = String.format(Locale.US, "file:///android_asset/%1$s", sticker);
        return Uri.parse(path);
    }

    public List<Sticker> getStickers() {
        return stickers;
    }

    public int size() {
        return stickers.size();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StickerPack{");
        sb.append("stickers=").append(stickers);
        sb.append('}');
        return sb.toString();
    }
}
