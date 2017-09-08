package sasd97.java_blog.xyz.background_picker.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sasd97.java_blog.xyz.background_picker.R;
import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.background_picker.viewholders.BackgroundViewHolder;
import sasd97.java_blog.xyz.background_picker.viewholders.GradientViewHolder;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;

/**
 * Created by alexander on 08/09/2017.
 */

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundViewHolder>
    implements OnItemClickListener<View> {

    private static final int UNSELECTED = -1;

    private int selectedItem = UNSELECTED;
    private List<BackgroundItem> backgroundItems = new ArrayList<>();

    private OnItemClickListener<BackgroundItem> listener;

    public void setListener(@NonNull OnItemClickListener<BackgroundItem> listener) {
        this.listener = listener;
    }

    public void addAll(@NonNull Collection<BackgroundItem> items) {
        int oldPosition = getItemCount();
        backgroundItems.addAll(items);
        notifyItemRangeInserted(oldPosition, getItemCount());
    }

    @Override
    public void onClick(View view, int position) {
        int lastItem = selectedItem;
        selectedItem = position;

        if (lastItem != UNSELECTED) notifyItemChanged(lastItem);
        notifyItemChanged(selectedItem);

        if (listener != null) listener.onClick(backgroundItems.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return backgroundItems.get(position).getType();
    }

    @Override
    public BackgroundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View v = LayoutInflater.from(context).inflate(R.layout.item_gradient, parent, false);
        return new GradientViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(BackgroundViewHolder holder, int position) {
        BackgroundItem item = backgroundItems.get(position);
        holder.setBackgroundItem(item);

        if (position == selectedItem) holder.select();
        else holder.deselect();
    }

    @Override
    public int getItemCount() {
        return backgroundItems.size();
    }
}
