package sasd97.java_blog.xyz.background_picker.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import sasd97.java_blog.xyz.background_picker.models.BackgroundItem;
import sasd97.java_blog.xyz.libs_common.utils.events.OnItemClickListener;

/**
 * Created by alexander on 08/09/2017.
 */

public abstract class BackgroundViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

    private OnItemClickListener<View> listener;

    public BackgroundViewHolder(@NonNull View itemView,
                                @NonNull OnItemClickListener<View> listener) {
        super(itemView);
        this.listener = listener;
    }

    public abstract void select();
    public abstract void deselect();

    public abstract void setBackgroundItem(@NonNull BackgroundItem item);

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition());
    }
}
