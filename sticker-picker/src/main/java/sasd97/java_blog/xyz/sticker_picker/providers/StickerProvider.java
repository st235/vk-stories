package sasd97.java_blog.xyz.sticker_picker.providers;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.sticker_picker.models.StickerPack;

/**
 * Created by alexander on 09/09/2017.
 */

public class StickerProvider implements Provider<List<StickerPack>> {

    private static final String STICKER_MARKER = "sticker_pack";

    private AssetManager assetManager;
    private List<StickerPack> stickers = new ArrayList<>();

    public StickerProvider(@NonNull AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    private void load() {
        if (stickers.size() > 0) return;

        try {
            String[] assetsFolders = assetManager.list("");
            for (String folder: assetsFolders) addFolder(folder);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<StickerPack> provide() {
        if (stickers.isEmpty()) load();
        return stickers;
    }

    public StickerPack getPack(int position) {
        return stickers.get(position);
    }

    public List<StickerPack> getStickers() {
        return stickers;
    }

    private void addFolder(@NonNull String path) throws IOException {
        if (!path.contains(STICKER_MARKER)) return;

        String[] stickers = assetManager.list(path);

        StickerPack pack = generatePack(path, stickers);
        this.stickers.add(pack);
    }

    private StickerPack generatePack(@NonNull String folder,
                                     @NonNull String[] stickers) {
        StickerPack stickerPack = new StickerPack();

        for (String sticker: stickers) {
            stickerPack.addSticker(folder, sticker);
        }

        return stickerPack;
    }
}
