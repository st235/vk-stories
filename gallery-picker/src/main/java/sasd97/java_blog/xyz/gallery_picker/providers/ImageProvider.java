package sasd97.java_blog.xyz.gallery_picker.providers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sasd97.java_blog.xyz.gallery_picker.models.Tile;
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;

/**
 * Created by alexander on 09/09/2017.
 */

public class ImageProvider implements Provider<List<Tile>> {

    private Context context;
    private int maxCounter = 25;
    private List<Tile> tiles = new ArrayList<>();

    public ImageProvider(@NonNull Context context) {
        this.context = context;
    }

    private void init() {
        Cursor imageCursor = null;
        try {
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
            final String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";

            imageCursor = context.getApplicationContext()
                    .getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

            if (imageCursor != null) {
                int count = 0;
                while (imageCursor.moveToNext() && count < maxCounter) {
                    String imageLocation = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File imageFile = new File(imageLocation);
                    tiles.add(new Tile(Uri.fromFile(imageFile)));
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageCursor != null && !imageCursor.isClosed()) {
                imageCursor.close();
            }
        }
    }

    @Override
    public List<Tile> provide() {
        if (tiles.isEmpty()) init();
        return tiles;
    }
}
