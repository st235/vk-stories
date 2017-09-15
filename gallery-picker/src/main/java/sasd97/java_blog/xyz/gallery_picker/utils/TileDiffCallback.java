package sasd97.java_blog.xyz.gallery_picker.utils;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

import sasd97.java_blog.xyz.gallery_picker.models.Tile;

/**
 * Created by alexander on 15/09/2017.
 */

public class TileDiffCallback extends DiffUtil.Callback {

    private List<Tile> oldList;
    private List<Tile> newList;

    public TileDiffCallback(@NonNull List<Tile> oldList, @NonNull List<Tile> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
