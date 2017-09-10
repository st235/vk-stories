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
import sasd97.java_blog.xyz.libs_common.utils.providers.Provider;
import sasd97.java_blog.xyz.libs_selectionview.providers.SelectionProvider;
import sasd97.java_blog.xyz.background_picker.viewholders.BackgroundViewHolder;
import sasd97.java_blog.xyz.background_picker.viewholders.GradientViewHolder;
import sasd97.java_blog.xyz.background_picker.viewholders.ImageViewHolder;
import sasd97.java_blog.xyz.background_picker.viewholders.PlusViewHolder;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;
import sasd97.java_blog.xyz.libs_selectionview.models.Selection;

/**
 * Created by alexander on 08/09/2017.
 */

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundViewHolder>
    implements OnItemClickListener<View> {

    private static final int UNSELECTED = -1;
    private static final int FIRST_ITEM = 0;

    private int selectedItem = FIRST_ITEM;

    private Provider<List<Selection>> selectionProvider;
    private List<BackgroundItem> backgroundItems = new ArrayList<>();

    private View.OnClickListener addListener;
    private OnItemClickListener<BackgroundItem> clickListener;

    public void setAddListener(@NonNull View.OnClickListener addListener) {
        this.addListener = addListener;
    }

    public void setClickListener(@NonNull OnItemClickListener<BackgroundItem> clickListener) {
        this.clickListener = clickListener;
    }

    public void add(@NonNull BackgroundItem item) {
        backgroundItems.add(item);
        notifyItemInserted(getItemCount());
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

        if (backgroundItems.get(position).getType() == BackgroundItem.PLUS) {
            if (addListener != null) addListener.onClick(view);
            return;
        }

        if (clickListener != null) clickListener.onClick(backgroundItems.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return backgroundItems.get(position).getType();
    }

    @Override
    public BackgroundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        selectionProvider = new SelectionProvider(context);

        if (viewType == BackgroundItem.IMAGE) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(v, this, selectionProvider);
        }

        if (viewType == BackgroundItem.PLUS) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_plus, parent, false);
            return new PlusViewHolder(v, this, selectionProvider);
        }

        View v = LayoutInflater.from(context).inflate(R.layout.item_gradient, parent, false);
        return new GradientViewHolder(v, this, selectionProvider);
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
