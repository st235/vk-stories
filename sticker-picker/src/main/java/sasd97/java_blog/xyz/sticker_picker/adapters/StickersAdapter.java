package sasd97.java_blog.xyz.sticker_picker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sasd97.java_blog.xyz.sticker_picker.R;
import sasd97.java_blog.xyz.sticker_picker.models.Sticker;
import sasd97.java_blog.xyz.sticker_picker.viewholders.StickersViewHolder;

/**
 * Created by alexander on 09/09/2017.
 */

public class StickersAdapter extends RecyclerView.Adapter<StickersViewHolder> {

    private List<Sticker> stickers = new ArrayList<>();

    public void addAll(@NonNull Collection<Sticker> stickers) {
        int oldSize = getItemCount();
        this.stickers.addAll(stickers);
        notifyItemRangeInserted(oldSize, getItemCount());
    }

    @Override
    public StickersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_sticker, parent, false);
        return new StickersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StickersViewHolder holder, int position) {
        Sticker sticker = stickers.get(position);
        holder.setSticker(sticker);
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }
}
